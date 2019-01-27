package cn.yanzhongxin.sso.service.impl;

import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.mapper.TbUserMapper;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.pojo.TbUserExample;
import cn.yanzhongxin.sso.service.LoginService;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;


/* @Author Zhongxin Yan
 * @Description To Do 登陆，进行用户名密码校验
 * @Date 2019/1/10 12:22
 * @Param 
 * @return 
 */
@Service
public class LoginServiceImpl  implements LoginService {
    @Autowired
    TbUserMapper tbUserMapper;

    @Autowired
    JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    public Integer SESSION_EXPIRE;
    @Override
    public E3Result login(String username, String password) {

        TbUserExample tbUserExample=new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));

        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
            if (tbUsers==null || tbUsers.size()==0){
                return E3Result.build(400,"用户名或密码错误");
            }

            //生成token
        String token = UUID.randomUUID().toString();
            //把用户信息写入redis ,key=token,value：用户信息
        TbUser tbUser = tbUsers.get(0);
        tbUser.setPassword(null);
        //设置session的过期时间
        jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(tbUser));
        jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);

        return E3Result.ok(token);//返回给表现层sessionid


    }
}
