/**package com.yuanerya.focusservice.Config;


import com.yuanerya.focusservice.interceptor.GateWayInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//用于注册网关验证拦截器
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private GateWayInterceptor gateWayInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(gateWayInterceptor).addPathPatterns("/**");  // 是否通过网关验证拦截器
    }

}**/
