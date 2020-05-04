package com.aynu.redis.Dao;

import com.aynu.redis.pojo.Animal;

public class InsertAnimalPrivider {

    public String insertAnimal(Animal animal){

        StringBuffer stringBuffer=new StringBuffer();

        stringBuffer.append("insert into animal(id,name,color,runmethd) values ");
        stringBuffer.append("("+animal.getId()+",");
        stringBuffer.append("'"+animal.getName()+"',");
        stringBuffer.append(""+animal.getColor().getId()+",");
        stringBuffer.append("'"+animal.getRunmethd()+"')");
        return stringBuffer.toString();

    }
}
