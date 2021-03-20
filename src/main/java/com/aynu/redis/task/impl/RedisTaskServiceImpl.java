package com.aynu.redis.task.impl;

import com.aynu.redis.dao.PurchaseService;
import com.aynu.redis.pojo.PurchaseRecordPo;
import com.aynu.redis.task.RedisTaskService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Auther: LC
 * @Date : 2021 03 02 16:13
 * @Description : com.aynu.redis.task.impl
 * @Version 1.0
 */
@Service
public class RedisTaskServiceImpl implements RedisTaskService {

    Logger logger=LoggerFactory.getLogger(RedisTaskServiceImpl.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PurchaseService purchaseService;

    private static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";

    private static final String PURCHASE_PRODUCT_LIST = "purchase_list_";

    // 每次取出1000条，避免一次取出消耗太多内存
    private static final int ONE_TIME_SIZE = 1000;

    @Scheduled(cron = "0 0 1 * * ?")
    @Override
    public void purchaseTask() {
        logger.info("存放redis数据开始了");
        //获取集合中的所有key值
        Set<String> products = redisTemplate.opsForSet().members(PRODUCT_SCHEDULE_SET);
        //获取所有的要插入的订单信息
        List<PurchaseRecordPo> list=new ArrayList<>();

        for (String productId:products
             ) {
            long productLong = Long.parseLong(productId);
            String purchaseKey=PURCHASE_PRODUCT_LIST+productLong;
            BoundListOperations<String,String> boundListOperations = redisTemplate.boundListOps(purchaseKey);
            //计算记录数
            Long size = redisTemplate.opsForList().size(purchaseKey);

            long time=size%ONE_TIME_SIZE==0?size:size/ONE_TIME_SIZE+1;

            for (int i = 0; i < time; i++) {
                //获取至多Time——stamp个抢红包信息
                List<String> purchaseList=null;
                if (i==0){
                    purchaseList = boundListOperations.range(i * ONE_TIME_SIZE, (i + 1) * ONE_TIME_SIZE);
                }else {
                    purchaseList =boundListOperations.range(i*ONE_TIME_SIZE+1,(i+1)*ONE_TIME_SIZE);
                }
                for (String purchase:purchaseList
                     ) {
                    PurchaseRecordPo purchaseRecord = this.createPurchaseRecord(productLong, purchase);
                    list.add(purchaseRecord);
                }
                try {
                    purchaseService.dealRedisPurchase(list);
                }catch (Exception e){
                    e.printStackTrace();
                }
                list.clear();
            }
            //删除购买列表
            redisTemplate.delete(purchaseKey);
            //从商品集合中删除商品
            redisTemplate.opsForSet().remove(PRODUCT_SCHEDULE_SET,productId);
        }
        logger.info("定时任务结束");


    }

    private PurchaseRecordPo createPurchaseRecord(Long productId, String prStr) {
        //使用，进行数据字段的拆分
        String[] arr = prStr.split(",");

         Long userId = Long.parseLong(arr[0]);
         int quantity = Integer.parseInt(arr[1]);
         double sum = Double.valueOf(arr[2]);
         double price = Double.valueOf(arr[3]);
         Long time = Long.parseLong(arr[4]);
         Timestamp purchaseTime = new Timestamp(time);
         PurchaseRecordPo pr = new PurchaseRecordPo();
         pr.setProductId(productId);
         pr.setPurchaseTime(purchaseTime);
         pr.setPrice(price);
         pr.setQuantity(quantity);
         pr.setSum(sum);
         pr.setUserId(userId);
         pr.setNote("购买日志，时间：" + purchaseTime.getTime());
         return pr;
     }
}
