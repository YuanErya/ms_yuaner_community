package com.yuanerya.starservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "cn.yuanerya.feign.clients")
@MapperScan("com.yuanerya.starservice.mapper")
public class StarServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarServiceApplication.class, args);
    }

}
