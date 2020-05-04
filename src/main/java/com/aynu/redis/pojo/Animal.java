package com.aynu.redis.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias("animal")
public class Animal implements Serializable {

    private Integer id;

    private String name;

    private ColorEnum color;

    private String runmethd;

    public Animal() {
    }

    public Animal(Integer id, String name, ColorEnum color, String runmethd) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.runmethd = runmethd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public String getRunmethd() {
        return runmethd;
    }

    public void setRunmethd(String runmethd) {
        this.runmethd = runmethd;
    }
}
