package com.aynu.redis.service;

import org.springframework.stereotype.Service;

/**
 * @author admin
 */
public interface ActiveMqService {

    /**
     * 发送消息
     */
    public void sentMessage(String message);

    /**
     * 接收消息
     */
    public void receiveMessage(String message);
}
