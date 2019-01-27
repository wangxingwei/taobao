package cn.yanzhongxin.cart.interceptor;

import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.sso.service.TokenService;
import cn.yanzhongxin.utils.CookieUtils;
import cn.yanzhongxin.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/16 16:14
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //执行handler之前，执行此方法
        //1.从cookie中取token,cookie得值
        String token = CookieUtils.getCookieValue(request, "token");

        //2 如果token中没有数据，未登录状态。直接放行
        if (StringUtils.isBlank(token)){
            return true;
        }
        //3 如果浏览器中取到了token数据的话，调用sso服务，根据token取用户信息
        E3Result userInfoByToken = tokenService.getUserInfoByToken(token);
        //4 如果没有取到用户信息，登陆过期。直接放行
        if (userInfoByToken.getStatus()!=200){
            //没取到用户信息，直接放行
            return true;
        }
        ///5 取到用户信息
        TbUser user =(TbUser) userInfoByToken.getData();
        //6 把用户信息放到request中
        request.setAttribute("user",user);
        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
