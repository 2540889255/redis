package com.aynu.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class Animal implements Serializable {


    @Value("小狗")
    public String name;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
