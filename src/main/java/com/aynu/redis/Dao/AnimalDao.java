package com.aynu.redis.Dao;

import com.aynu.redis.pojo.Animal;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AnimalDao {

    @Select({"select * from animal"})
    public List<Animal> getAnimals();

    //@Insert({"insert into animal(id,name,color,runmethd) values (#{id},#{name},#{color},#{runmethd})"})
    @InsertProvider(type = InsertAnimalPrivider.class,method = "insertAnimal")
    public int insertAnimal(Animal animal);

    @UpdateProvider(type = UpdateAnimalPrivider.class,method = "updateAnimal")
    public int updateAnimal(Animal animal);

    @Delete("delete from animal where id =#{id}")
    public int deleteAnimal(@Param("id") Long id);

    @Select("select * from animal where id=#{id}")
    public Animal getAnimal(@Param("id") Long id);
}
