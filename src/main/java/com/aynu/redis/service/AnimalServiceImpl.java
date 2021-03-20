package com.aynu.redis.service;

import com.aynu.redis.dao.AnimalDao;
import com.aynu.redis.pojo.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService{

    @Autowired
    AnimalDao animalDao;

    @Override
    @Transactional
    //@Cacheable(value = "redisCache",key = "'redis_user'+#result.id")
    public List<Animal> showAnimals() {
        List<Animal> animal = animalDao.getAnimals();
        return animal;
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT )
    @Cacheable(value = "redisCache",key = "'redis_user'+#id")
    public Animal insertAnimal(Animal animal) {

        animalDao.insertAnimal(animal);
        return animal;
    }

    @Override
    @Transactional
    @CachePut(value = "redisCache",condition = "#result!='null'",key = "'result_user'+#id")
    public Animal updateAnimal(Animal animal) {
        animalDao.updateAnimal(animal);
        return animal;
    }

    @Override
    @Transactional
    @CacheEvict(value = "redisCache",key = "'result_user'+#id",beforeInvocation = false)
    public int deleteAnimal(Long id) {
        int i = animalDao.deleteAnimal(id);
        return i;
    }

    @Override
    @Transactional
    @Cacheable(value = "redisCache",key = "'redult_user'+#id")
    public Animal getAnimal(Long id) {
        Animal animal = animalDao.getAnimal(id);
        return animal;
    }


}
