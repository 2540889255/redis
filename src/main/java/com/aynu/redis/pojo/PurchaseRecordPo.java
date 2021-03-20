package com.aynu.redis.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:16
 * @Description : com.aynu.redis.pojo
 * @Version 1.0
 */
@Alias("purchaseRecord")
public class PurchaseRecordPo implements Serializable {

    private static final long serialVersionUID= -360816189433370174L;;

    private String id;

    private long userId;

    private long productId;

    private double price;

    private int quantity;

    private double sum;

    private Timestamp purchaseTime;

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Timestamp getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Timestamp purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
