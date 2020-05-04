package com.aynu.redis.service;


import com.aynu.redis.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class ActiveMqUserServiceImpl implements ActiveMqUserSerive{

    //注入spring自动生产的jmstemplate
    @Autowired
    JmsTemplate jmsTemplate;

    //自定义地址
    private static final String myDestination = "my-destination";

    @Override
    public void sendUser(Message message) {
        System.out.println("发送了消息"+message.getUsername());
        jmsTemplate.convertAndSend(myDestination,message);
    }

    @JmsListener(destination = myDestination)
    @Override
    public void receiveUser(Message message) {
        System.out.println("消息的内容是"+message.getNote());
    }
}
