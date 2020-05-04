package com.aynu.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * @author admin
 */
@Controller
@RequestMapping("/websockets")
public class WebSocketsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 发送界面
     */
    @GetMapping("/send")
    public String send(){
        return "/websockets/send";
    }

    /**
     * 接受界面
     */
    @GetMapping("/receive")
    public String receive(){
        return "/websockets/receive";
    }

    /**
     * 对特定用户发送界面
     */
    @GetMapping("/sendUser")
    public String sendUser(){
        return "/websockets/send-user";
    }

    /**
     * 接收用户消息的界面
     */
    @GetMapping("/receiveUser")
    public String receiveUser(){
        return "/websockets/receive-user";
    }

    /**
     * 定义消息请求路径
     * 定义结果发送到特定的路径
     */
    @MessageMapping("/send")
    @SendTo("/sub/chat")
    public String sendMsg(String value){
        System.out.println("你发送消息到达了这");
        return value;
    }

    /**
     * 将消息发送给特定用户
     */
    @MessageMapping("/sendUser")
    public void sendToUser(Principal principal,String body){
        String srcUser=principal.getName();
        //解析用户的信息
        String[] args = body.split(",");
        String desUser=args[0];
        String message="["+srcUser+"]给你发来消息:"+args[1];
        //发送到用户和监听地址
        simpMessagingTemplate.convertAndSendToUser(desUser,"/queue/customer",message);
    }



}
