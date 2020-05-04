package com.aynu.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        //消息体
        String body=new String(message.getBody());
        //渠道名称
        String topic=new String(bytes);

        System.out.printf(body);
        System.out.printf(topic);
    }
}
