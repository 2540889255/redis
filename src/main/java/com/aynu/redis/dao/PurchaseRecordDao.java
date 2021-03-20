package com.aynu.redis.dao;

import com.aynu.redis.pojo.PurchaseRecordPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:34
 * @Description : com.aynu.redis.Dao
 * @Version 1.0
 */
@Mapper
public interface PurchaseRecordDao {


    public int insertPurchaseRecord(PurchaseRecordPo purchaseRecordPo);
}
