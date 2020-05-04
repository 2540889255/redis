package com.aynu.redis.service;

import com.aynu.redis.pojo.Message;
import com.aynu.redis.pojo.User;

/**
 * @author admin
 */
public interface ActiveMqUserSerive {

    /**
     * 发送消息
     * @param message
     */
    public void sendUser(Message message);

    /**
     * 接收消息
     * @param message
     */
    public void receiveUser(Message message);
}
