package com.aynu.redis.Dao;

import com.aynu.redis.pojo.Animal;

public class UpdateAnimalPrivider {

    public String updateAnimal(Animal animal){
        StringBuffer str=new StringBuffer();

        str.append("update animal set name="+"'"+animal.getName()+"',");
        str.append("color="+"'"+animal.getColor().getId()+"',");
        str.append("runmethd="+"'"+animal.getRunmethd()+"' ");
        str.append("where id="+"'"+animal.getId()+"'");
        System.out.println(str.toString());
        return str.toString();
    }
}
