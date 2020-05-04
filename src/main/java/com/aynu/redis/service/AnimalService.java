package com.aynu.redis.service;

import com.aynu.redis.pojo.Animal;

import java.util.List;

public interface AnimalService {

    public List<Animal> showAnimals();

    public Animal insertAnimal(Animal animal);

    public Animal updateAnimal(Animal animal);

    public int deleteAnimal(Long id);

    public Animal getAnimal(Long id);
}
