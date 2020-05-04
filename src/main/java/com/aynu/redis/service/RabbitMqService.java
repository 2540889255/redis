package com.aynu.redis.service;

import com.aynu.redis.pojo.Message;

/**
 * @author admin
 */
public interface RabbitMqService {

    /**
     * 发送字符串消息
     * @param message
     */
    public void sendMsg(String message);

    /**
     * 发送Message类的消息
     * @param message
     */
    public void sendMessage(Message message);
}
