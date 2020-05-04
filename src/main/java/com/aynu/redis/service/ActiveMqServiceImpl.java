package com.aynu.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class ActiveMqServiceImpl implements ActiveMqService{

    /**
     * 注入springboot 自动生成的jmstemplate
     */

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sentMessage(String message) {
        System.out.println("发送消息");
        jmsTemplate.convertAndSend(message);
        //自定义发送地址
        //jmsTemplate.convertAndSent(message)
    }

    /**
     * 使用注解监听地址发送过来的消息
     * @param message
     */
    @Override
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMessage(String message) {
        System.out.println("接收到消息"+message);
    }
}
