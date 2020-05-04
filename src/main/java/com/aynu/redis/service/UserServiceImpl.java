package com.aynu.redis.service;

import com.aynu.redis.Dao.UserDao;
import com.aynu.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void printPerson(User user) {
        System.out.println("当前的用户是"+user.getUser_name());
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userDao.getUsers();
        return users;
    }
}
