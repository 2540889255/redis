package com.aynu.redis.core;

public interface RedisScript<T> {

    //获取脚本的shall
    String getShall();

    //获取脚本的返回值
    Class<T> getResultType();
    //获取脚本的字符串
    String getScriptAsString();
}
