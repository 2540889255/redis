package com.aynu.redis.controller;

import com.aynu.redis.exception.NotFoundException;
import com.aynu.redis.pojo.Animal;
import com.aynu.redis.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    AnimalService animalService;
    @RequestMapping("/testAttribute")
    public void testAttribute(){
        System.out.println("你的controller到达了这");
        throw new RuntimeException("异常了，跳转到控制器通知的异常信息里");
    }

    @RequestMapping("/post")
    public String getPostHtml(){
        return "/post/postrequestheader";
    }

    @PostMapping("/getAnimal")
    @ResponseBody
    public Animal getAnimal(@RequestHeader("id")Long id){
        System.out.println("恭喜你到达了这");
        Animal animal = animalService.getAnimal(id);
        if (animal==null){
            throw new NotFoundException(1L,"找不到用户"+id+"的信息");
        }
        System.out.println(animal);
        return animal;
    }

    @GetMapping("/getAnimal/1")
    @ResponseBody
    public Animal getAnimal1(){
        System.out.println("恭喜你到达了这");
        Animal animal = animalService.getAnimal(1L);
        if (animal==null){
            throw new NotFoundException(1L,"找不到用户的信息");
        }
        System.out.println(animal);
        return animal;
    }


    public static Animal getAnimals(){
        RestTemplate restTemplate=new RestTemplate();
        Animal animal = restTemplate.getForObject("http://localhost:8086/test/getAnimal/1", Animal.class);
        //打印用户的 名称
        System.out.println(animal.getName());
        return animal;
    }
    public static void insertAnimal(){
        //设置请求主体里面的Animal实例
        Animal animal=new Animal();
        RestTemplate restTemplate=new RestTemplate();
        //请求头
        HttpHeaders headers=new HttpHeaders();
        //设置请求内容为json格式
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //设置请求实体对象
        HttpEntity<Animal> request =new HttpEntity<Animal>(animal,headers);
        Animal resultAnimal = restTemplate.postForObject("http://localhost:8086/test/getAnimal/1", request, Animal.class);
    }
}
