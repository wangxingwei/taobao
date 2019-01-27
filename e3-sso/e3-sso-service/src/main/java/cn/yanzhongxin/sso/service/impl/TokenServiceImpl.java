package cn.yanzhongxin.sso.service.impl;

import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.sso.service.TokenService;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/* @Author Zhongxin Yan
 * @Description To Do  根据token,查询redis缓存
 * @Date 2019/1/10 18:09
 * @Param 
 * @return 
 */
@Service
public class TokenServiceImpl implements TokenService {
   @Autowired
   private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    public Integer SESSION_EXPIRE;
    @Override
    public E3Result getUserInfoByToken(String id) {
        //根据token查询用户信息

        String s = jedisClient.get("SESSION:" + id);
        if (StringUtils.isBlank(s)){
            //取不到信息，说明登陆过期
            return  E3Result.build(201,"用户登录过期");
        }
        //取到用户信息,重置session过期时间
        jedisClient.expire("SESSION:"+id,SESSION_EXPIRE);
        //查询不到的话，返回过期登陆
        TbUser tbUser = JsonUtils.jsonToPojo(s, TbUser.class);
        return  E3Result.ok(tbUser);

    }
}
