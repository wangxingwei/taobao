package cn.yanzhongxin.portal.controler;

import cn.yanzhongxin.content.service.ContentService;
import cn.yanzhongxin.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/3 13:33
 */
@Controller
public class IndexControler {
    //访问首页，首先web.xml中有index.html。由于是html结尾，因此会被前端控制器拦截，因此会被拦截到这里

    @Autowired
    ContentService contentService;

    @Value("${category_lunbo}") //Spring外部配置文件，修改分类id
    long lunboCid;

    @Value("${category_lunbo_right}") //Spring外部配置文件，修改分类id
    long category_lunbo_right; ////首页打广告轮播图右侧广告



    //首页访问，访问数据库。加载首页的广告，写到首页。
    @RequestMapping("/index")
    public String showIndex(Model model){

        List<TbContent> tbContents = contentService.queryContentsByCategoryId(lunboCid);
        List<TbContent> ad2 = contentService.queryContentsByCategoryId(category_lunbo_right);

        model.addAttribute("ad2List",ad2);//首页打广告轮播图右侧
        model.addAttribute("ad1List",tbContents);//首页轮播图

        return "index";//返回index会被视图解析器，解析为WEB-INF/jsp/index.jsp
    }
}
