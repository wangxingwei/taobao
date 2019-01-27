package cn.yanzhongxin.sso.controler;


import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.sso.service.RegisterService;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.PriorityQueue;

/* @Author Zhongxin Yan
 * @Description To Do  开始用户注册
 * @Date 2019/1/10 10:22
 * @Param 
 * @return 
 */
@Controller
public class RegisterControler {
    @Autowired  //注入表现层中调用dubbo接口的服务,变量名称就是服务中的接口id
    // 服务的提供者，中规定了，接口的具体实现类
    private RegisterService registerService;


    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";//显示注册页面逻辑视图
    }
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody //路径变量，第一个是参数，第二个是参数需要校验的类型
    public E3Result checkData(@PathVariable String param,@PathVariable Integer type){
        E3Result e3Result = registerService.checkData(param, type);
        return e3Result;
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public E3Result register(TbUser tbUser){
        E3Result register = registerService.register(tbUser);
        return  register;
    }
}
