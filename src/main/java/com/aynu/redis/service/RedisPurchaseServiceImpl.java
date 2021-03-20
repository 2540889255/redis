package com.aynu.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Auther: LC
 * @Date : 2021 03 01 21:12
 * @Description : com.aynu.redis.service
 * @Version 1.0
 */
@Service
public class RedisPurchaseServiceImpl implements RedisPurchaseService{


    @Autowired
    RedisTemplate redisTemplate;

    String purchaseScript="redis.call";

    @Override
    public boolean purchaseProduct() {




        return false;
    }
}
