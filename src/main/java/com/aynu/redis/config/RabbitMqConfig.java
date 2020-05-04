package com.aynu.redis.config;


import org.springframework.amqp.core.Queue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * @author admin
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 消息队列名称
     */
    //@Value("${rabbitmq.queue.msg}")
    private String msgQueueName="msgRouting";

    /**
     * 用户队列名称
     */
    //@Value("${rabbitmq.queue.message}")
    public String messageQueueName="messageRouting";

    /**
     * 创建字符串消息队列，boolean值代表的是是否持久化消息
     */
    @Bean
    public Queue creatQueueMsg(){
        //true是为默认开启持久化
        return new Queue(msgQueueName,true);
    }

    /**
     * 创建用户消息队列 boolean代表的是是否持久化
     * @return
     */
    @Bean
    public Queue creatQueueMessage(){
        //true是为默认开启持久化
        return new Queue(messageQueueName,true);
    }


}
