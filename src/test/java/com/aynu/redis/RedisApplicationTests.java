package com.aynu.redis;

import com.aynu.redis.config.Animal;
import com.aynu.redis.config.Config;
import com.aynu.redis.config.RedisConfig;
import com.aynu.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    User user;
    @Test
    void contextLoads() {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate=ctx.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForHash().put("hash","field1","hvalue");
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
