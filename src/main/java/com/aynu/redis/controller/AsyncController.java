package com.aynu.redis.controller;

import com.aynu.redis.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/report")
    @ResponseBody
    public void AsyncReport(){
        asyncService.generationReport();
        System.out.println("当前打印报表的线程是"+Thread.currentThread().getName());
    }

}
