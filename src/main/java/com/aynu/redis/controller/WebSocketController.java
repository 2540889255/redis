package com.aynu.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author admin
 */
@Controller
@RequestMapping("websocket")
public class WebSocketController {


    @GetMapping("/index")
    public String webSocket(){
        return "/websocket/websocket";
    }
}
