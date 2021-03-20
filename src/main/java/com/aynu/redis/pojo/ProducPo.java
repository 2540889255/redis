package com.aynu.redis.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @Auther: LC
 * @Date : 2021 03 01 12:12
 * @Description : com.aynu.redis.pojo
 * @Version 1.0
 */
@Alias("product")
public class ProducPo implements Serializable {

    private static final long serialVersionUID= 3288311147760635602L;

    private Long id;

    private String productName;

    private int stock;

    private double price;

    private long version;

    private String note;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ProducPo{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", note='" + note + '\'' +
                '}';
    }
}
