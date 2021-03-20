package com.aynu.redis;

import com.aynu.redis.config.Animal;
import com.aynu.redis.config.Config;
import com.aynu.redis.config.RedisConfig;
import com.aynu.redis.pojo.People;
import com.aynu.redis.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.*;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    User user;


    /*测试对象的序列化*/
    @Test
    public void testObjectSerial(){
        Animal animal=new Animal();
        animal.setName("luchen");
        redisTemplate.opsForValue().set("animal",animal);
        Object o = redisTemplate.opsForValue().get("animal");
        System.out.println(o);

    }


    /*redisHashMap的操作*/
    @Test
    public void test10(){
        Set keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
        Map map=new HashMap();
        map.put("id","1");
        map.put("stock","10000");
        map.put("price","5");

        redisTemplate.opsForHash().putAll("product_1",map);
        //stringRedisTemplate.opsForHash().put("hashMaps","name","xioalan");
        //Object o = redisTemplate.opsForHash().get("hashMap", "name");
        //System.out.println((String) o);
    }


    @Test
    void contextLoads() {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate=ctx.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForHash().put("hash","field1","hvalue");
    }

    @Test
    public void testLettence() throws JsonProcessingException {
        /*测试redis的序列化*/

        People people=new People();

        people.setId(1);
        people.setName("陆晨");
        String s = new ObjectMapper().writeValueAsString(people);
        System.out.println(s);
        redisTemplate.opsForValue().set("user:"+people.getId(),s);

        System.out.println(redisTemplate.opsForValue().get(people.getId()));
    }

    @Test
    public void testLettenceserial() throws JsonProcessingException {
        /*测试redis的序列化*/

        People people=new People();

        people.setId(2);
        people.setName("陆晨");
        String s = new ObjectMapper().writeValueAsString(people);
        System.out.println(s);
        stringRedisTemplate.opsForValue().set("user:"+people.getId(),s);

        System.out.println(stringRedisTemplate.opsForValue().get(String.valueOf("user:"+people.getId())));
    }

    /*测试redis事务*/
    @Test
    public void testRedistransaction() throws JSONException {
        Jedis jedis=new Jedis("127.0.0.1",6379);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username","luchen");
        jsonObject.put("password","123456");

        //开启事务
        Transaction multi = jedis.multi();
        String jsonString = jsonObject.toString();
        System.out.println(jsonString);
        try {
            multi.set("user:1",jsonString);
            multi.exec();

        }catch (Exception e){
            multi.discard();
        }finally {
            jedis.close();
        }

    }

    @Test
    public void test(){
        //创建jedis对象
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String ping = jedis.ping();
        System.out.println(ping);

    }


    @Test
    void testRedis(){
        //redisTemplate.opsForValue().set("my1",1);
        stringRedisTemplate.opsForValue().set("int111","1");
        stringRedisTemplate.opsForValue().increment("int111");
        //redisTemplate.opsForValue().increment("my1");
        //redisTemplate.opsForValue().increment("my2");
        Jedis connection = (Jedis)redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        //connection.decr("int");
    }

    @Test
    void testRedisadd(){
        stringRedisTemplate.opsForList().leftPush("topic1","2");
    }

    @Test
    public void testBean(){
        ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        Animal animal = context.getBean(Animal.class);
        System.out.printf(animal.name);
        /*User user = context.getBean(User.class);
        System.out.printf(user.getName());*/
        System.out.printf(user.getUser_name());
    }
    @Test
    public void test1(){
        System.out.printf(user.getUser_name());
    }


    @Test
    public void testArrayList(){
        List<List<String>> listList=new ArrayList();
        List<String> StringList1=new ArrayList();
        List<String> StringList2=new ArrayList();
        List<String> StringList3=new ArrayList();

        StringList1.add("1");
        StringList1.add("11");
        StringList2.add("2");
        StringList2.add("22");
        StringList3.add("3");
        StringList3.add("33");

        listList.add(StringList1);
        listList.add(StringList2);
        listList.add(StringList3);

        System.out.println(listList);
    }

    @Test
    public void testArrayListObject(){
        List<List<Animal>> listList=new ArrayList();
        List<Animal> StringList1=new ArrayList();
        List<Animal> StringList2=new ArrayList();
        List<Animal> StringList3=new ArrayList();

        StringList1.add(new Animal());
        StringList1.add(new Animal());
        StringList1.add(new Animal());
        StringList2.add(new Animal());
        StringList2.add(new Animal());
        StringList3.add(new Animal());
        StringList3.add(new Animal());

        listList.add(StringList1);
        listList.add(StringList2);
        listList.add(StringList3);

        System.out.println(listList);
    }

    @Test
    public void testString(){
        String a="123";

        String b=123+"";
        System.out.println(a==b);
        System.out.println(a.equals(b));
    }

    @Test
    public void test2(){
        String a="123";

        String b=new String(String.valueOf("123".hashCode()));
        System.out.println(b.hashCode());
        String c=new String("123");

        System.out.println(a==b);
        System.out.println(b==c);
    }
}
