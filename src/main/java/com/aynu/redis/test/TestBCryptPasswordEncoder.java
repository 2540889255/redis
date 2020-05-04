package com.aynu.redis.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCryptPasswordEncoder {


    public static void main(String[] args) {
        String password = "123456";

        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

        String encode = passwordEncoder.encode(password);
        System.out.println(encode);

        Boolean admin = passwordEncoder.matches("123456", encode);
        System.out.println(admin);


    }
}
