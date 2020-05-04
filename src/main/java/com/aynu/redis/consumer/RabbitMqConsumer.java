package com.aynu.redis.consumer;

import com.aynu.redis.pojo.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author admin
 */
@Component
public class RabbitMqConsumer {

    /**
     * 消息的消费接收者
     * @param msg
     */
    //@RabbitListener(queues = {"${rabbitmq.queue.msg}"})
    @RabbitListener(queues = {"msgRouting"})
    public void receiveMsg(String msg){
        System.out.println("收到消息"+msg);
    }

    /**
     * 消息类的消费接收者
     * @param message
     */
    //@RabbitListener(queues = { "${rabbit,queue.message}" })
    @RabbitListener(queues = { "messageRouting" })
    public void receiveMessage(Message message){
        System.out.println("收到了消息类的消息"+message.getNote());
    }
}
