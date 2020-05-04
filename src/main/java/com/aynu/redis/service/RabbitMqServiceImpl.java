package com.aynu.redis.service;

import com.aynu.redis.pojo.Message;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 实现ConfirmCallback接口实现回调
 * @author admin
 */
@Service
public class RabbitMqServiceImpl implements RabbitTemplate.ConfirmCallback,RabbitMqService{

    //@Value("${rabbitmq.queue.msg}")
    //public String msgRouting = null;
    public static final String msgRouting = "msgRouting";

    //@Value("${rabbitmq.queue.message}")
    //public String messageRouting=null;
   public static final String messageRouting="messageRouting";

    /**
     * 自动注入rabbitmq的自动配置
     */
    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public void sendMsg(String message) {
        System.out.println("发送消息"+message);
        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(msgRouting,message);
    }


    @Override
    public void sendMessage(Message message) {
        System.out.println("发送消息类"+message.getUsername());
        //设置回调
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend(messageRouting,message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if (b){
            System.out.println("消息成功消费");
        }else {
            System.out.println("消息消费失败"+ s);
        }
    }
}
