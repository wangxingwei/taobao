package cn.yanzhongxin.order;


import cn.yanzhongxin.cart.service.CartService;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.sso.service.TokenService;
import cn.yanzhongxin.utils.CookieUtils;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/* @Author Zhongxin Yan
 * @Description To Do  登陆的拦截器,负责拦截所有，必须保证用户必须登陆
 * @Date 2019/1/17 10:31
 * @Param 
 * @return 
 */
public class LoginIntercepter  implements HandlerInterceptor{

    @Value("${SSO_URL}")
    String SSO_URL;

    @Autowired
    TokenService tokenService;

    @Autowired
    CartService cartService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //1 从客户端浏览器检查是否有token
        String token = CookieUtils.getCookieValue(request, "token");
        //判断token是否存在
        if (StringUtils.isBlank(token)){
            //如果token不存在，用户肯定没有登陆，让用户跳转到登陆页面,如果用户
            //跳转后登陆成功还得返回到当前的 页面
            response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
            //拦截，不让你登陆
            return false;
        }

        //如果token存在的话，调用调用单点登陆系统，取出token信息
        E3Result userInfoByToken = tokenService.getUserInfoByToken(token);
        TbUser user = (TbUser) userInfoByToken.getData();
        //如果没取到的话，说明登陆过期了，需要用户重新登陆
        if (userInfoByToken.getStatus()!=200){
            //登陆过期，需要重新登陆
            response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
            //拦截，不让你登陆
            return false;
        }
        request.setAttribute("user",user);//把用户信息放到request中
        //如果用户已经登陆的话，判断浏览器中的购物车数据，合并到用户的购物车中
        String cartJson = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isNoneBlank(cartJson)){//合并cookie中的购物车，和数据库中的购物车
           List<TbItem> cartitems = JsonUtils.jsonToList(cartJson, TbItem.class);
           cartService.mergetCart(user.getId(),cartitems);
       }

        return true;//放行
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
