package com.aynu.redis.controller;

import com.aynu.redis.pojo.Message;
import com.aynu.redis.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 */
@Controller
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Autowired
    RabbitMqService rabbitMqService;

    @RequestMapping("/sentMsg")
    @ResponseBody
    public void sentMsg(){
        String msg="这是一条简单的消息";
        rabbitMqService.sendMsg(msg);
    }

    @RequestMapping("/sentMessage")
    @ResponseBody
    public void sentMessage(){
        Message message=new Message(1L,"消息","今台南市晴天");
        rabbitMqService.sendMessage(message);
    }
}
