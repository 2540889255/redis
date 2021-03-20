package com.aynu.redis;

import com.aynu.redis.dao.PurchaseService;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:11
 * @Description : com.aynu.redis
 * @Version 1.0
 */
@SpringBootTest
public class T_ProductTest {

    @Autowired
    PurchaseService purchaseService;
    @Test
    public void test(){


        purchaseService.purchase(1L,1L,1);



    }

    /*模拟高并发情况下，消费商品*/
    @Test
    public void test1() throws InterruptedException {


        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            Thread thread=new Thread(() ->{
                for (int j = 0; j < 3; j++) {
                    purchaseService.purchase(1L,1L,1);
                }
            });
            thread.start();
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        Thread.sleep(100000);
    }

}



