package com.yuanerya.userservice.interceptor;


import cn.yuanerya.feign.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//用于验证用户端所携带的token，判断是否登录
@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o) throws IOException {
        try {
            if(isProtectedUrl(request)) {
//                System.out.println(request.getMethod());
                if(!request.getMethod().equals("OPTIONS")) {
                    JwtUtil.validateTokenAndAddUserIdToHeader(request);
                    return true;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // System.out.println("视图渲染之后的操作");
    }

    private boolean isProtectedUrl(HttpServletRequest request) {
        List<String> protectedPaths = new ArrayList<String>();
        //向list中添加需要保护拦截的请求
        protectedPaths.add("/user/info");
        protectedPaths.add("/user/getFootprint");
        protectedPaths.add("/user/logout");
        protectedPaths.add("/question/*");
        boolean bFind = false;
        for( String passedPath : protectedPaths ) {
            bFind = pathMatcher.match(passedPath, request.getServletPath());
            if( bFind ) {
                break;
            }
        }
        return bFind;
    }

}
