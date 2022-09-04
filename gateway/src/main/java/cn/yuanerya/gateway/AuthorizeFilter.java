package cn.yuanerya.gateway;


import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class AuthorizeFilter implements GlobalFilter {

    public static final String SECRET = "ThisIsASecret";
    public static final String CHECK="WhetherHaveBeenToGateway";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME = "userName";

    //用于匹配路径
    private static AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //无需拦截的url
        String check=generateToken("create");
        if(needLogin(request.getPath().toString())){
            //加入生成经过网关验证的头
            request.mutate().header("CheckGateway",check).build();
            //讲生成的验证同时加入缓存
            stringRedisTemplate.opsForValue().set("cache:gateway:",check,60, TimeUnit.SECONDS);
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(HEADER_STRING);
        //如果传来的token为”“或者没传token
        if(token==null||token==""){
            return fail(exchange);
        }
        try{
            //对token进行解析
            String name=parseToken(token);
            //将解析出的信息加入到HEADER中
            request.mutate().header(USER_NAME,name).build();
        }catch(Exception e){
            return fail(exchange);
        }
        //加入生成经过网关验证的头
        request.mutate().header("CheckGateway",check).build();
        //讲生成的验证同时加入缓存
        stringRedisTemplate.opsForValue().set("cache:gateway:",check,60, TimeUnit.SECONDS);
        return chain.filter(exchange);
    }

    /**
     * 设置不需要拦截的地址
     * @param uri
     * @return
     */
    public static boolean needLogin(String uri){
        // test
        List<String> uriList = new ArrayList<>();
        uriList.add("/user/login");
        uriList.add("/user/register");
        for (String pattern : uriList) {
            if (matcher.match(pattern, uri)) {
                // 不需要拦截
                return true;
            }
        }
        return false;
    }


    /**
     * 根据token解析出userNaME
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        String userName;
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
            userName=String.valueOf(body.get(USER_NAME));
        return userName;
    }

    /**
     * 生成token,
     * 此处生成的token是用于校验请求是否经过网关
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(USER_NAME, userId);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_00L))//设置了登录超过期限100H
                .signWith(SignatureAlgorithm.HS512,CHECK)
                .compact();
        return jwt; //jwt前面一般都会加Bearer
    }

    /**
     * token校验失败，返回错误信息
     * @param exchange
     * @return
     */

    public static Mono<Void> fail(ServerWebExchange exchange) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 401);
        resultJson.put("message", "请重新登陆授权");
        resultJson.put("status", 401);
        ServerHttpResponse response = exchange.getResponse();
        byte[] bytes = JSONObject.toJSONBytes(resultJson);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}
