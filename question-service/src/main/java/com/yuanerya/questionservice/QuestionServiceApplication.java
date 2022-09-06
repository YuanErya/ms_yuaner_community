package com.yuanerya.questionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.yuanerya.feign.clients")
@MapperScan("com.yuanerya.questionservice.mapper")
public class QuestionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuestionServiceApplication.class, args);
    }


    //使用json序列化化消息
    @Bean
    public MessageConverter messageConverter (){
        return new Jackson2JsonMessageConverter();
    }
}
