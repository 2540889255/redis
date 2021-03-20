package com.aynu.redis.dao;

import com.aynu.redis.pojo.ProducPo;
import com.aynu.redis.pojo.PurchaseRecordPo;
import com.aynu.redis.until.RandomOrderUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:39
 * @Description : com.aynu.redis.Dao
 * @Version 1.0
 */

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    ProductDao productDao;

    @Autowired
    PurchaseRecordDao purchaseRecordDao;


    /*使用乐观锁*/
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean purchase(Long userId, long productId, int quantity) {
        ProducPo product=null;
        while (true) {
             product = productDao.getProduct(productId);
             if (product.getStock()>0) {
                 boolean result = productDao.decreaseProductByVersion(productId, quantity, product.getVersion());

                 if (result = true) {
                     break;
                 }
             }else {
                 return false;
             }

        }
        System.out.println(product.toString());
        return true;
    }


    //当运行方法启用新的独立事务运行
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean dealRedisPurchase(List<PurchaseRecordPo> prpList) {
     for (PurchaseRecordPo prp : prpList) {
         purchaseRecordDao.insertPurchaseRecord(prp);
         productDao.decreaseProduct(prp.getProductId(), prp.getQuantity());
     }
     return true;
    }

















    /*原逻辑*/
    /*@Transactional(isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRES_NEW)
    @Override
    public synchronized boolean  purchase(Long userId, long productId, int quantity) {
        Boolean result;
        ProducPo product = productDao.getProduct(productId);
        int stock = product.getStock();

        if (stock>0){
            result = productDao.decreaseProduct(productId, quantity);

            PurchaseRecordPo purchaseRecordPo=new PurchaseRecordPo();
            purchaseRecordPo.setId(RandomOrderUntil.creatRandomOrderId());
            purchaseRecordPo.setPrice(product.getPrice());
            purchaseRecordPo.setSum(product.getPrice()*quantity);
            purchaseRecordPo.setQuantity(quantity);
            purchaseRecordPo.setUserId(userId);
            purchaseRecordPo.setProductId(productId);




            if (result!=false){
                purchaseRecordDao.insertPurchaseRecord(purchaseRecordPo);
            }
        }

        System.out.println(product.toString());

        return true;
    }*/
}
