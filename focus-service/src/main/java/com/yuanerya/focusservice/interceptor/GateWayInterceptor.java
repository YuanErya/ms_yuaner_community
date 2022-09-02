package com.yuanerya.focusservice.interceptor;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

//用于验证是否通过网关的拦截器
@Component
public class GateWayInterceptor implements HandlerInterceptor {
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o) throws IOException {
        try {
            if(isProtectedUrl(request)) {
                //取到网关验证
                String check=request.getHeader("CheckGateway");
                if(StrUtil.isNotBlank(check)){
                if(check.equals(stringRedisTemplate.opsForValue().get("cache:gateway:"))){
                    return true;
                }
                return false;
                }
                else{
                    return false;
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
        protectedPaths.add("/**");
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
