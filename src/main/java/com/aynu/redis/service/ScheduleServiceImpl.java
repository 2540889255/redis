package com.aynu.redis.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class ScheduleServiceImpl {

    /**
     * 计数器
     */
    int count1 = 1;
    int count2 = 2;

    /**
     * 每隔一秒钟执行一次
     * 使用异步执行
     */
    @Scheduled(fixedRate = 1000)
    @Async
    public void job1(){
        System.out.println("job每分钟执行一次");
    }


}
