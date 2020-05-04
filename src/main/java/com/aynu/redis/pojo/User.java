package com.aynu.redis.pojo;

import com.aynu.redis.config.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {

    int user_id;

    String user_name;

    int user_infocol;

    public User() {
    }

    public User(int user_id, String user_name, int user_infocol) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_infocol = user_infocol;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_infocol() {
        return user_infocol;
    }

    public void setUser_infocol(int user_infocol) {
        this.user_infocol = user_infocol;
    }
}
