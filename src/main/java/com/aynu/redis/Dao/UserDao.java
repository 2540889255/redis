package com.aynu.redis.Dao;

import com.aynu.redis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user_info")
    public List<User> getUsers();
}
