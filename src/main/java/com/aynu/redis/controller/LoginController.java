package com.aynu.redis.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author admin
 */
@RequestMapping("/login")
@Controller
public class LoginController {

    @RequestMapping("/userlogin")
    public String userLogin(@RequestParam("username") String username,@RequestParam("password") String password){
        if ("luchen".equals(username)&&password.equals("123456")){
            return "main";
        }

        return "login";
    }

    @RequestMapping("/main")
    public String getMain(){
        return "main";
    }
}
