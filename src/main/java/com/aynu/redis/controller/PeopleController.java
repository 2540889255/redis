package com.aynu.redis.controller;

import com.aynu.redis.mapper.PeopleRepository;
import com.aynu.redis.pojo.People;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/peoples")
    public String getPeoples(Model model, HttpServletResponse response){
        //response.addCookie(new Cookie("peoples","xxxxx"));
        Collection<People> peoples=peopleRepository.findAll();
        model.addAttribute("peoples",peoples);
        return "main";
    }

    @RequestMapping("/testsession")
    public String testSession(HttpSession session){
        //session.setAttribute("testsession","this is a session");
        return "main";
    }

    @RequestMapping("/testgetsession")
    public String testGetSeeion(HttpSession session){
        //Object object=session.getAttribute("testsession");
        //System.out.printf(object.toString());
        return "main";
    }

    @RequestMapping("/showredis")
    @ResponseBody
    public Map<String,String> showRedis(){
        long now =System.currentTimeMillis();

        List list = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {


            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    redisOperations.opsForValue().set("a" + i, "content" + i);
                    redisOperations.opsForValue().get("a" + i);
                    if (i == 100000) {
                        System.out.printf("redis测试已经结束");
                    }
                }
                return null;
            }
        });



        long end=System.currentTimeMillis();
        System.out.printf("耗时"+(end=now));
        Map<String,String> map=new HashMap<>();
        map.put("result","success");
        return map;
    }

    @RequestMapping("/redislitener")
    public String testMessageListener(){
        //stringRedisTemplate.opsForList().leftPush("topic1","2");
        stringRedisTemplate.convertAndSend("topic1","afgeua");
        return "main";
    }

    @RequestMapping
    public String testBean(){
        //BeanFactory
        return null;
    }
}
