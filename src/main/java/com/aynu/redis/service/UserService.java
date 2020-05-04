package com.aynu.redis.service;

import com.aynu.redis.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public void printPerson(User user);

    public List<User> getUsers();
}
