package cn.yuanerya.gateway;


import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
public class AuthorizeFilter implements GlobalFilter {

    public static final String SECRET = "ThisIsASecret";//please change to your own encryption secret.
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME = "userName";

    //用于匹配路径
    private static AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //无需拦截的url
        if(needLogin(request.getPath().toString())){
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(HEADER_STRING);
        //如果传来的token为”“或者没传token
        if(token==null||token==""){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//设置状态码
            return exchange.getResponse().setComplete();
        }
        try{
            //对token进行解析
            String name=parseToken(token);
            //将解析出的信息加入到HEADER中
            request.mutate().header(USER_NAME,name).build();
        }catch(Exception e){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);//设置状态码
            return exchange.getResponse().setComplete();
        }
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
}
