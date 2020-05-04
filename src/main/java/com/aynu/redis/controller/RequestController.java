package com.aynu.redis.controller;

import com.aynu.redis.pojo.Animal;
import com.aynu.redis.pojo.User;
import com.aynu.redis.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 这个控制器的目的是 对于客户端的请求返回对应的响应信息
 */

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    AnimalService animalService;

    @GetMapping("/Animal/1")
    public ResponseEntity<Animal> getAnimal(
            //@RequestBody Animal animal
    ){
        Animal animal1=new Animal();
        Animal result = animalService.getAnimal(1L);
        HttpHeaders headers=new HttpHeaders();
        String success=(result==null || result.getId()==null)? "false" : "true";
        //设置响应头，比较常用的方式
        headers.add("success",success);
        //下面是使用集合（List）方式，不是太常用
        //header.put("success",Arrays.asList(success))
        //返回创建的时候的状态码
        return new ResponseEntity<Animal>(result,headers, HttpStatus.ACCEPTED);
    }

    @GetMapping("/Animal/2")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal getAnimalStatus(
            //@RequestBody Animal animal
    ){
        Animal animal1=new Animal();
        Animal result = animalService.getAnimal(1L);
        return result;
    }



}
