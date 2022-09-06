package com.yuanerya.questionservice.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//绑定交换机和队列
//@Configuration
public class MQFanoutConfig {
    //@Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("yuaner.star");
    }

    //@Bean
    public Queue starToStar(){
        return new Queue("star.toStar");
    }
    //@Bean
    public Binding fanoutBing1(Queue starToStar,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(starToStar).to(fanoutExchange);
    }

    //@Bean
    public Queue starToUser(){
        return new Queue("star.toUser");
    }
    //@Bean
    public Binding fanoutBing2(Queue starToUser,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(starToUser).to(fanoutExchange);
    }
}
