package cn.yanzhongxin;

import cn.yanzhongxin.jedis.JedisClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/5 14:27
 */
public class JedisPoolTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        JedisClient bean = applicationContext.getBean(JedisClient.class);
        bean.set("testJedisPool","zhongxin");

        System.out.println(bean.get("testJedisPool")+bean.getClass());


    }
}
