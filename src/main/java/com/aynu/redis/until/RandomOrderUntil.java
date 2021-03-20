package com.aynu.redis.until;

import java.util.UUID;

/**
 * @Auther: LC
 * @Date : 2021 03 01 15:32
 * @Description : com.aynu.redis.until
 * @Version 1.0
 */
public class RandomOrderUntil {


    public static String creatRandomOrderId(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        RandomOrderUntil.creatRandomOrderId();
    }
}
