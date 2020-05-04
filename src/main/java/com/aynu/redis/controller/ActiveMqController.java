package com.aynu.redis.controller;

import com.aynu.redis.pojo.Message;
import com.aynu.redis.service.ActiveMqService;
import com.aynu.redis.service.ActiveMqUserSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 */
@Controller
@RequestMapping("/activemq")
public class ActiveMqController {

    @Autowired
    ActiveMqService activeMqService;

    @Autowired
    ActiveMqUserSerive activeMqUserSerive;

    @RequestMapping("/sendMessages")
    @ResponseBody
    public void sentMessages(){
        activeMqService.sentMessage("这只是一条消息");
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public void sentMassage(){
        Message message=new Message(1L,"这是我的名字","这是我的内容");
        activeMqUserSerive.receiveUser(message);
    }
}
