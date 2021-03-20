package com.aynu.redis.dao;

import com.aynu.redis.pojo.ProducPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:22
 * @Description : com.aynu.redis.Dao
 * @Version 1.0
 */
@Mapper
public interface ProductDao {

    //这里需要传参数id，如果一个表中有许多的种类的物品，那个就需要通过id来决定需要扣减的是哪个商品
    public ProducPo getProduct(long id);

    //这里的扣减，quantity是因为，在扣减的时候可能不是购买一件，可能是多件，所以需要扣减quantity数量的商品
    public boolean decreaseProduct(long id,int quantity);

    public boolean decreaseProductByVersion(long id,int quantity,long version);

}
