package com.aynu.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;


@Configuration
public class RedisConfig {

    /*@Autowired
    private RedisConnectionFactory redisConnectionFactory=null;
    */
    @Autowired
    private RedisMessageListener messageListener;

    //任务池
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private RedisConnectionFactory connectionFactory;

   /* @Bean(name = "RedisConnectionFactory")
    public RedisConnectionFactory initRedisConnectionFactory(){
        if (this.connectionFactory!=null){
            return this.connectionFactory;
        }
        JedisPoolConfig config =new JedisPoolConfig();
        //最大空闲数
        config.setMaxIdle(30);
        //最大连接数
        config.setMaxTotal(50);
        //最大等待毫秒数
        config.setMaxWaitMillis(2000);
        //创建jedis连接工厂
        JedisConnectionFactory connectionFactory=new JedisConnectionFactory(config);
        //获取淡季的redis配置
        RedisStandaloneConfiguration redisStandaloneConfiguration=connectionFactory.getStandaloneConfiguration();
        connectionFactory.setHostName("127.0.0.1");
        connectionFactory.setPort(6379);
        connectionFactory.setPassword("");
        this.connectionFactory=connectionFactory;
        System.out.printf("默认的bean的工厂被使用了");
        return connectionFactory;
    }*/

    /*自定义redis运行模板*/
    @Bean("redisTemplate")
    public RedisTemplate<Object,Object> initRedisTemplate(){
        System.out.println("欢迎来到自定义redistemplate");
        RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
        //Redistemplate 会自动初始化StringRedisSerializer
        /*创建json2objectserial序列化器*/
        Jackson2JsonRedisSerializer serializer=new Jackson2JsonRedisSerializer(Object.class);
        //RedisSerializer serializer=redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        //redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        redisTemplate.setConnectionFactory(connectionFactory);
        System.out.printf("默认的template被使用了");
        return redisTemplate;
    }

    /**
     *     创建线程池 运行线程处理redis消息
     */


    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler(){
        if (taskScheduler!=null){
            return taskScheduler;
        }
        taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }


    /**
     *
     * @return
     */

    @Bean
    public RedisMessageListenerContainer initMessageListenerContainer(){
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        //redis连接工厂
        container.setConnectionFactory(connectionFactory);
        //运行池
        container.setTaskExecutor(initTaskScheduler());
        //定义监听渠道 名称topicl
        Topic topic=new ChannelTopic("topic1");
        //使用监听器监听redis消息
        container.addMessageListener(messageListener,topic);
        System.out.printf("默认的监听器被初始化了");
        return container;
    }
    /**
     * 自定义redis注解缓存器
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager initRedisCacheManage(){
        //reids加锁的写入器
        RedisCacheWriter writer=RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        //启动redis缓存的默认设置
        RedisCacheConfiguration configuration=RedisCacheConfiguration.defaultCacheConfig();
        //设置jdk序列化器
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        configuration=configuration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializationRedisSerializer)
        );

        configuration= configuration.disableKeyPrefix();
        //设置10min超时
        configuration=configuration.entryTtl(Duration.ofMinutes(10));
        //创建缓redis存管理器
        RedisCacheManager redisCacheManager=new RedisCacheManager(writer,configuration);
        System.out.println("自定义的缓存管理器生效了");
        return redisCacheManager;
    }
        //禁用前缀

}
