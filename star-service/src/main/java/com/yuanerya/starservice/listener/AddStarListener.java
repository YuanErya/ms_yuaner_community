package com.yuanerya.starservice.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Map;

public class AddStarListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "star.toStar"),     //监听队列
            exchange = @Exchange(name = "yuaner.star", type = ExchangeTypes.DIRECT),
            key = {"all", "toStar"}))
    public void listenDirectQueue1(Map<String,String> msg) {

    }


}
