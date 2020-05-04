package com.aynu.redis.pojo;

public enum ColorEnum {

    RED(1,"红色"),
    BLUE(2,"蓝色"),
    WHITE(3,"白色"),
    DARK(4,"黑色");

    private int id;

    private String color;

    ColorEnum(int id,String color) {
        this.id=id;
        this.color=color;
    }

    public static ColorEnum getColor(int id){
        for (ColorEnum e:ColorEnum.values()
             ) {
            if (e.id==id){
                return e;
            }
        }
        return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
