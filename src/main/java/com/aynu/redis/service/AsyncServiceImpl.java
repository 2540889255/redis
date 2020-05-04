package com.aynu.redis.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService{


    @Override
    @Async
    public void generationReport() {
        System.out.println("当前生成报表的线程的是"+Thread.currentThread().getName());
    }
}
