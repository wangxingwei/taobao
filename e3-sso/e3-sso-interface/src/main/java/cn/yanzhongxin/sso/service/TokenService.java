package cn.yanzhongxin.sso.service;


import cn.yanzhongxin.utils.E3Result;

/* @Author Zhongxin Yan
 * @Description To Do 根据token查询redis缓存数据
 * @Date 2019/1/10 18:08
 * @Param 
 * @return 
 */
public interface TokenService {
    E3Result getUserInfoByToken(String id);
}
