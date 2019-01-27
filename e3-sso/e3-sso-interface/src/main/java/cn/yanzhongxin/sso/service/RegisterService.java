package cn.yanzhongxin.sso.service;


import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.utils.E3Result;

/* @Author Zhongxin Yan
 * @Description To Do 用户注册，进行数据检查，开始注册
 * @Date 2019/1/10 10:58
 * @Param 
 * @return 
 */
public interface RegisterService {
    E3Result checkData(String param,int type);//进行用户名，手机号是否已经存在检查

    //真正进行表单的注册
    E3Result register(TbUser tbUser);
}
