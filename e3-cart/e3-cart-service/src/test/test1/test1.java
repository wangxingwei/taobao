package test1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/16 17:13
 */
public class test1 {
    public static void main(String[] args) {
        JedisPool jedisPool=new JedisPool("192.168.25.128",6379);
        Jedis resource =jedisPool.getResource();
        String addItem = resource.hget("CART"+":"+"ADFAS" + "", "FASDF" + "");
        System.out.println(addItem);
    }
}
