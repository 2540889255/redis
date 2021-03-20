package com.aynu.redis.dao;

import com.aynu.redis.pojo.PurchaseRecordPo;

import java.util.List;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:37
 * @Description : com.aynu.redis.Dao
 * @Version 1.0
 */
public interface PurchaseService {

    public boolean purchase(Long userId,long productId,int quantity);

    public boolean dealRedisPurchase(List<PurchaseRecordPo> list);
}
