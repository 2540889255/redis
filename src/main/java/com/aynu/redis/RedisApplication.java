package com.aynu.redis;

import com.aynu.redis.service.UserService;
import com.aynu.redis.service.UserServiceImpl;
import com.aynu.redis.test.TestRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@EnableCaching
@SpringBootApplication
@EnableScheduling
//@ComponentScan(basePackages = "com.aynu.redis.Dao")
//@MapperScan(basePackages = "com.aynu.redis",annotationClass = Repository.class)
public class RedisApplication {

    /*@Autowired
    private RedisTemplate redisTemplate=null;
*/


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedisApplication.class, args);


    }



    /*@PostConstruct
    public void init(){
        initRedisTemplate();
    }

    public void initRedisTemplate(){
        RedisSerializer serializer=redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        System.out.printf("默认的redistemplate被初始化了");
    }*/
}
