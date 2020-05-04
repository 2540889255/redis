package com.aynu.redis.controller;

import com.aynu.redis.pojo.Animal;
import com.aynu.redis.pojo.ColorEnum;
import com.aynu.redis.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    AnimalService animalService;
    @Transactional
    @RequestMapping("/getAnimals")
    @ResponseBody
    public List<Animal> getAnimals(){
        List<Animal> animals = animalService.showAnimals();
        for (Animal animal:animals) {
            //animal
        }
        return  animals;
    }

    @RequestMapping("/insertAnimal")
    @ResponseBody
    public void insertAnimal(){
        Animal animal=new Animal();
        animal.setId(5);
        animal.setName("小2黑");
        animal.setRunmethd("2奥");
        animal.setColor(ColorEnum.BLUE);
        animalService.insertAnimal(animal);
    }

    @RequestMapping("/updateAnimal")
    @ResponseBody
    public void updateAnimal(){
        Animal animal=new Animal();
        animal.setId(12);
        animal.setColor(ColorEnum.BLUE);
        animal.setName("更新的动物");
        animal.setRunmethd("这是一需要更新的动物");
        animalService.updateAnimal(animal);
    }

    @RequestMapping("/deleteAnimal")
    @ResponseBody
    public void deleteAnimal(){
        animalService.deleteAnimal(12L);
    }

    @PostMapping("/getAnimal")
    @ResponseBody
    public void getAnimal(@RequestHeader("id")Long id){
        System.out.println("到达了这");
        Animal animal = animalService.getAnimal(id);
        System.out.println(animal);
    }
}
