package com.aynu.redis.test;

import com.aynu.redis.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestRedis {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    /*public void test(){

        User user=new User();
        user.setUser_id(1);
        user.setUser_name("luchen");

        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));

        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper, false);

        redisTemplate.opsForHash().putAll("user", jackson2HashMapper.toHash(user));

        Map map = redisTemplate.opsForHash().entries("user");

        User user1 = objectMapper.convertValue(map, User.class);
        System.out.println(user1.getUser_id());
        System.out.println(user1.getUser_name());
    }*/

    /*public static void main(String[] args) {

        TestRedis testRedis=new TestRedis();
        testRedis.test();

    }*/
}
