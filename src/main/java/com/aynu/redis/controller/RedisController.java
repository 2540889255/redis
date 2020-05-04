package com.aynu.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/getLists")
    @ResponseBody
    public List<String> setRedisAndGet(){
        stringRedisTemplate.opsForValue().set("k1","1");
        BoundValueOperations<String, String> k1 = stringRedisTemplate.boundValueOps("k1");

        k1.append("111");
        Object o = stringRedisTemplate.opsForHash().get("hash2", "key1");
        System.out.println((String) o);
        List list=new ArrayList();
        list.add(o);;
        return list;
    }

    @RequestMapping("zset")
    @ResponseBody
    public Map<String,Object> testZset(){
        Set<ZSetOperations.TypedTuple<String>> typedTuples=new HashSet<>();
        for (int i = 0; i < 9; i++) {
            //分数
            double score=i*0.1;
            //创建一个typeTuple对象，存入值和分数
            ZSetOperations.TypedTuple typedTuple=new DefaultTypedTuple<String>("value"+i,score);
            typedTuples.add(typedTuple);
        }
        //往游戏机和插入元素
        stringRedisTemplate.opsForZSet().add("zset1", typedTuples);
        System.out.println(typedTuples);
        //绑定zset有序集合操作
        BoundZSetOperations<String, String> zSetOperations = stringRedisTemplate.boundZSetOps("zset1");
        zSetOperations.add("value10",0.26);
        Set<String> setRange = zSetOperations.range(1, 6);
        System.out.println("这是range 1-6的元素"+setRange);
        Set<String> setScore = zSetOperations.rangeByScore(0.2, 0.6);
        System.out.println("这是score在 0.2-0.6之间的元素"+setScore);
        //定义值范围
        Double score=zSetOperations.score("value8");
        System.out.println("value8的score的值为"+score);
        //在下标区间下 按分数排序  同时返回 value 和score
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = zSetOperations.rangeWithScores(1, 6);
        System.out.println("在下标区间下 按分数排序  同时返回 value 和score"+rangeWithScores.toArray().toString());
        //在分数区间下 按照分数排序  同时返回 value和score
        Set<ZSetOperations.TypedTuple<String>> rangeByScoreWithScores = zSetOperations.rangeByScoreWithScores(1, 6);
        System.out.println("在分数区间下 按照分数排序  同时返回 value和score"+rangeByScoreWithScores.toArray().toString());
        Set<String> reverseRange = zSetOperations.reverseRange(2, 8);
        System.out.println("reverse后的"+reverseRange.toString());
        Map map=new HashMap();
        map.put("secess",true);
        return map;
    }

    /**
     * 这个是测试spring使用事务机制
     */
    @RequestMapping
    @ResponseBody
    public void testMulti() throws Exception{
        stringRedisTemplate.opsForValue().set("key1","value1");

        List list=(List)stringRedisTemplate.execute(new SessionCallback<Object>(){

            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {
                //设置要监控的key1
                operations.watch("key1");
                //开启事务在实物提交之前 全部都进入队列之中
                operations.multi();
                operations.opsForValue().set("key2","value2");
                //监控key1 是否在监控之后被修改过 如果是则不执行事务  否则提交实物
                return operations.exec();
            }
        });
    }

    @RequestMapping("/redislua")
    @ResponseBody
    public Map<String,Object>  testLua(){
        DefaultRedisScript<String> rs =new DefaultRedisScript<>();
        rs.setScriptText("return 'hello redis'");
        rs.setResultType(String.class);
        RedisSerializer<String> stringRedisSerializer=redisTemplate.getStringSerializer();
        //执行lua脚本
        String str=(String) redisTemplate.execute(rs,stringRedisSerializer,stringRedisSerializer,null);

        Map map=new HashMap();
        map.put("str",str);
        return map;
    }
    /**
     * 测试带有参数的lua脚本
     */
    @RequestMapping("/redislua2/{key1}/{key2}/{value1}/{value2}")
    @ResponseBody
    public Map<String,Object> getRedisLuaWithArgs(@PathVariable("key1") String key1, @PathVariable("key2") String key2, @PathVariable("value1") String value1, @PathVariable("value2") String value2){

        String lua="redis.call('set',KEYS[1],ARGV[1]) \n"+
                "redis.call('set',KEYS[2],ARGV[2]) \n"+
                "local str1=redis.call('get',KEYS[1]) \n"+
                "local str2=redis.call('get',KEYS[2]) \n"+
                "if str1==str2 then  \n"+
                "return 1 \n"+
                "end \n"+
                "return 0 \n";
        System.out.println(lua);
        DefaultRedisScript rs=new DefaultRedisScript();
        rs.setResultType(Long.class);
        rs.setScriptText(lua);
        //采用字符串序列化器
        RedisSerializer<String> stringRedisSerializer=redisTemplate.getStringSerializer();
        //定义key参数
        List<String> keyList =new ArrayList<>();
        keyList.add(key1);
        keyList.add(key2);
        //传递两个参数值  其中丢一个序列化器是key的序列化器 第二个序列化器是参数的序列化器
        Long result =(Long)redisTemplate.execute(rs, stringRedisSerializer, stringRedisSerializer, keyList, value1, value2);

        Map<String,Object> map= new HashMap<>();

        map.put("result",result);
        return map;

    }

    @RequestMapping("/redisargs/{args}")
    @ResponseBody
    public void getArgs(@PathVariable("args") String args){
        System.out.println(args);
    }
}
