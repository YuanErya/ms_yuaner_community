package cn.yuanerya.feign.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 3600_000_00L; // 100 hour
    public static final String SECRET = "ThisIsASecret";//please change to your own encryption secret.
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String USER_NAME = "userName";

    public static String generateToken(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(USER_NAME, userId);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//设置了登录超过期限100H
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return jwt; //jwt前面一般都会加Bearer
    }

    /**
     * 根据token解析出userNaME
     * @param token
     * @return
     */
    public static String parseToken(String token) {
        Map<String, Object> body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        String userName=String.valueOf(body.get(USER_NAME));
        return userName;
    }



}
