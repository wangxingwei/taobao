package cn.yanzhongxin.sso.controler;

import cn.yanzhongxin.sso.service.LoginService;
import cn.yanzhongxin.utils.CookieUtils;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/* @Author Zhongxin Yan
 * @Description To Do  用户登录相关的操作
 * @Date 2019/1/10 12:08
 * @Param 
 * @return 
 */
@Controller
public class PageLogin {
    @Autowired
    LoginService loginService;//利用dubbo调用服务层的注册服务，返回false或者用户sessionid

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    @RequestMapping("page/login")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }
    @RequestMapping(value = "user/login",method = RequestMethod.POST )
    @ResponseBody
    public E3Result Login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        E3Result login = loginService.login(username, password);//登录结果，成功，或者失败
        if (login.getStatus()==200){
           //登陆状态成功
            //获得登陆成功的token，即sessionID
            String token = login.getData().toString();

            CookieUtils.setCookie(request,response,TOKEN_KEY,token);; //cookie的名称，cookie的值
        }

        return  login;

    }

}
