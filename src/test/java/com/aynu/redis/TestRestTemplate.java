package com.aynu.redis;

import com.aynu.redis.controller.TestController;
import com.aynu.redis.pojo.Animal;
import org.junit.jupiter.api.Test;

public class TestRestTemplate {

    @Test
    public void test(){
        Animal animals = TestController.getAnimals();
        System.out.println(animals.getName());
    }



}
