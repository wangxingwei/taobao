package cn.yanzhongxin.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author www.yanzhongxin.com
 * @date 2018/10/30 15:02
 */
@Controller
public class ShowPage {
    @RequestMapping("/")
    public String showIndePage(){
       return  "index";
    }

    //后台管理员页面，点击哪个按钮，返回哪个页面
    @RequestMapping("/{page}")
    public String showThePage(@PathVariable String page){
        System.out.println("hello word");
        System.out.println(page);
        return page;
    }
}
