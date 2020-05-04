package com.aynu.redis.controller;

import com.aynu.redis.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/change")
@Controller
public class RedirectController {

    @RequestMapping("/main")
    public String main(){
        System.out.println("欢迎你到达了主界面");
        return "redirect:/change/redirect1";
    }

    @RequestMapping("/redirect1")
    public String redirect1(RedirectAttributes attributes){
        User user=new User();
        user.setUser_name("21312");
        System.out.println("欢迎你到达了redirect的第一个界面");
        attributes.addFlashAttribute("string",String.valueOf(154));
        return "redirect:/change/redirect2";
    }

    @RequestMapping("/redirect2")
    public String redirect2(String string){
        System.out.println("欢迎你到达了redirect的第二个界面");
        System.out.println(string);
        return "/Redirect/redirect2";
    }
}
