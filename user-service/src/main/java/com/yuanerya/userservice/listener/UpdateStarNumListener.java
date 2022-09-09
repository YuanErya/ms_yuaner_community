package com.yuanerya.userservice.listener;

import cn.yuanerya.feign.model.entity.YeUser;
import com.yuanerya.userservice.mapper.YeUserMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateStarNumListener {
    @Autowired
    private YeUserMapper yeUserMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "star.toUser"),     //监听队列
            exchange = @Exchange(name = "yuaner.star", type = ExchangeTypes.DIRECT),
            key = {"all", "toUser"}))
    public void listenDirectQueue1(YeUser msg) {

        yeUserMapper.updateById(msg);
    }
}
