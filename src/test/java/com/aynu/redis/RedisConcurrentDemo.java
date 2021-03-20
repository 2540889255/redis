package com.aynu.redis;

import com.aynu.redis.pojo.Animal;
import com.aynu.redis.service.AnimalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: LC
 * @Date : 2021 03 02 15:58
 * @Description : com.aynu.redis
 * @Version 1.0
 */
@SpringBootTest
public class RedisConcurrentDemo {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AnimalServiceImpl animalService;
    // 先将产品编号保存到集合中
    String purchaseScript =
         " redis.call('sadd', KEYS[1], ARGV[2]) \n"
                 + "local productPurchaseList = KEYS[2]..ARGV[2] \n" // 购买列表
                 // 用户编号
                 // 用户编号
         + "local userId = ARGV[1] \n"

         + "local product = 'product_'..ARGV[2] \n"// 产品键
         // 购买数量
         + "local quantity = tonumber(ARGV[3]) \n"
         // 当前库存
         + "local stock = tonumber(redis.call('hget', product, 'stock')) \n"
         // 价格
         + "local price = tonumber(redis.call('hget', product, 'price')) \n"
         // 购买时间
         + "local purchase_date = ARGV[4] \n"
         // 库存不足，返回0
         + "if stock < quantity then return 0 end \n"
         // 减库存
         + "stock = stock - quantity \n"
         + "redis.call('hset', product, 'stock', tostring(stock)) \n"
         // 计算价格
         + "local sum = price * quantity \n"
         // 合并购买记录数据
            + "local purchaseRecord = userId..','..quantity..','"
         + "..sum..','..price..','..purchase_date \n"
         //将购买记录保存到list里
         + "redis.call('rpush', productPurchaseList, purchaseRecord) \n"
         // 返回成功
         + "return 1 \n";
    // Redis购买记录集合前缀
    private static final String PURCHASE_PRODUCT_LIST = "purchase_list_";
    // 抢购商品集合
    private static final String PRODUCT_SCHEDULE_SET = "product_schedule_set";



    // 32位SHA1编码，第一次执行的时候先让Redis进行缓存脚本返回
    private String sha1 = null;

    public boolean purchaseRedis(Long userId, Long productId, int quantity) {

        /*Long purchaseDate = System.currentTimeMillis();

        DefaultRedisScript redisScript = new DefaultRedisScript();


        redisScript.setScriptText(purchaseScript);
        redisScript.setResultType(Long.class);

        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();

        List<String> list=new ArrayList<>();
        list.add(PRODUCT_SCHEDULE_SET);
        list.add(PURCHASE_PRODUCT_LIST);

        Long result = (Long)redisTemplate.execute(redisScript,stringSerializer,stringSerializer,list, userId + "", productId + "",quantity + "", purchaseDate + "");
        System.out.println(result);*/

        // 购买时间
        Long purchaseDate = System.currentTimeMillis();
        Jedis jedis = null;

         try {
            // 获取原始连接
            //jedis=new Jedis("127.0.0.1",6379);
             stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
         // 如果没有加载过，则先将脚本加载到Redis服务器，让其返回sha1
         if (sha1 == null) {
             sha1 = jedis.scriptLoad(purchaseScript);
         }
         // 执行脚本，返回结果
         Object res = jedis.evalsha(sha1, 2, PRODUCT_SCHEDULE_SET,PURCHASE_PRODUCT_LIST, userId + "", productId + "",quantity + "", purchaseDate + "");
         Long result = (Long) res;
         return result == 1;
        } finally {
             // 关闭jedis连接
             if (jedis != null && jedis.isConnected()) {
                jedis.close();
             }
        }

    }

    @Test
    public void test() throws InterruptedException {
        long start = System.currentTimeMillis();
        purchaseRedis(1L,1L,1);
        /*for (int i = 0; i < 500; i++) {
            Thread thread=new Thread(() ->{
                for (int j = 0; j < 3; j++) {
                    purchaseRedis(1L,1L,1);
                }
            });
            thread.start();
        }*/
        Thread.sleep(100000);
    }

    @Test
    public void testLua() {
        DefaultRedisScript<String> rs = new DefaultRedisScript<String>();
        // 设置脚本
        /*String lua="redis.call('set',ARGV[1],KEYS[1])\n"
                +  "return \"hello\"";*/
        String lua="local product = 'product_'..ARGV[2] \n"
                    +"return product";
        rs.setScriptText(lua);
        // 定义返回类型。注意：如果没有这个定义，Spring不会返回结果
        rs.setResultType(String.class);
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        // 执行Lua脚本

        List<String> list=new ArrayList<>();
        list.add("k8");
        String values="1";
        String value1="2";
        String value2="3";
        String value3="4";
        String str = (String) redisTemplate.execute(rs, stringSerializer, stringSerializer, list,values,value1,value2,value3);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("str", str);
        System.out.println(str);
    }

    @Test
    public void test2() {
        String k1 = "k1";
        String k2 = "k2";
        String value1 = "haha";
        String value2 = "haha";


        DefaultRedisScript redisScript = new DefaultRedisScript();

        String lua = "redis.call('set',KEYS[1],ARGV[1])\n"
                + "redis.call('set',KEYS[2],ARGV[2])\n"
                + "local str1=redis.call('get',KEYS[1])\n"
                + "local str2=redis.call('get',KEYS[2])\n"
                + "if str1==str2 then\n"
                + "return 1\n"
                + "end\n"
                + "return 0\n"
                ;
        redisScript.setScriptText(lua);
        redisScript.setResultType(Long.class);

        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();

        List list=new ArrayList();
        list.add(k1);
        list.add(k2);

        Long result = (Long)redisTemplate.execute(redisScript, stringSerializer, stringSerializer, list, value1, value2);
        System.out.println(result);
    }

    @Test
    public void test3(){
        Animal animal = animalService.getAnimal(1L);
    }



    @Test
    public void test4() {
        String k1 = "k1";
        String k2 = "k2";
        String value1 = "haha";
        String value2 = "haha";


        DefaultRedisScript redisScript = new DefaultRedisScript();

        String lua = "redis.call('hget', product, 'stock')"
                ;
        redisScript.setScriptText(lua);
        redisScript.setResultType(Long.class);

        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();

        List list=new ArrayList();
        list.add(k1);
        list.add(k2);

        Long result = (Long)redisTemplate.execute(redisScript, stringSerializer, stringSerializer, list, value1, value2);
        System.out.println(result);
    }

}
