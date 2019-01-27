package cn.yanzhongxin.sso.service;


import cn.yanzhongxin.utils.E3Result;

/* @Author Zhongxin Yan
 * @Description To Do 用户登录相关的信息
 * @Date 2019/1/10 12:20
 * @Param 
 * @return 
 */
public interface LoginService {
    E3Result login(String username,String password);
}
