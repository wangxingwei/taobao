package cn.yanzhongxin.sso.service.impl;

import cn.yanzhongxin.mapper.TbUserMapper;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.pojo.TbUserExample;
import cn.yanzhongxin.sso.service.RegisterService;
import cn.yanzhongxin.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


/* @Author Zhongxin Yan
 * @Description To Do 用户注册，数据校验，开始用户注册
 * @Date 2019/1/10 11:00
 * @Param 
 * @return 
 */
@Service("registerServiceImpl")
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    TbUserMapper userMapper;//利用逆向工程
    
    
    /* @Author Zhongxin Yan
     * @Description To Do用户提交表单的数据校验
     * @Date 2019/1/10 11:40
     * @Param 
     * @return 
     */
    @Override
    public E3Result checkData(String param, int type) {
        //type是数据校验的类型，1是用户名，2是手机号，3是邮箱
        TbUserExample tbUserExample=new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if (type==1){ //开始校验用户名
            criteria.andUsernameEqualTo(param);
        }else if (type==2)//校验手机号
        {
            criteria.andPhoneEqualTo(param);
        }else if (type==3) {
            //开始校验邮箱
            criteria.andEmailEqualTo(param);
        }else {
            return E3Result.build(400,"数据不合法");
            //数据不合法
        }
        List<TbUser> tbUsers = userMapper.selectByExample(tbUserExample);
        //查询到的结果，可能查到集合有数据，可能没有查到集合中没有数据
        if (tbUsers!=null&&tbUsers.size()>0){
            //如果有数据返回false
            return  E3Result.ok(false);//返回false
        }else {
            //如果没有重复的数据返回true
            return E3Result.ok(true);//
        }
    }


    
    
    /* @Author Zhongxin Yan
     * @Description To Do 进行表单注册
     * @Date 2019/1/10 11:44
     * @Param 
     * @return 
     */
    @Override
    public E3Result register(TbUser tbUser) {
        //数据完整性(数据是否为空)，数据有效性（数据库中是否存在）进行校验,
        if (StringUtils.isBlank(tbUser.getUsername())||StringUtils.isBlank(tbUser.getPassword())||StringUtils.isBlank(tbUser.getPhone())){
            return E3Result.build(400,"注册信息不完成,请从新注册");
        }

        E3Result e3Result = checkData(tbUser.getUsername(), 1);
        if (!(Boolean) e3Result.getData()){
            return E3Result.build(400,"用户名重复,请从新注册");
        }
        e3Result = checkData(tbUser.getPhone(), 2);
        if (!(Boolean) e3Result.getData()){
            return E3Result.build(400,"手机号已经被占用，请从新注册");
        }

        //完成用户的完整性，有效性验证了
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        userMapper.insert(tbUser);
        return E3Result.ok();
    }
}
