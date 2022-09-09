package com.yuanerya.questionservice.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//RabbitMQ的消息回调
@Slf4j
@Configuration
public class RabbitMQConfig {
@Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);

    rabbitTemplate.setMandatory(true);//设置强制回调
    rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (!ack) {
                log.error("消息发送异常! correlationData={} ,ack={}, cause={}", correlationData.getId(), ack, cause);
            }else {
                log.info("消息发送成功");
            }
        }
    });
    rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            log.error("returnedMessage ===> replyCode={} ,replyText={} ,exchange={} ,routingKey={}", replyCode, replyText, exchange, routingKey);
        }
    });
    return rabbitTemplate;
}
}