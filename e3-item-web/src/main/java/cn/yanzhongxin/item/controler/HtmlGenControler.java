package cn.yanzhongxin.item.controler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/9 19:49
 */
@Controller
public class HtmlGenControler {
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer; //配置文件对象，里面注入了模板的文件夹路径

    @RequestMapping("genhtml")
    @ResponseBody
    public String genHtml() throws  Exception{
        // 1、从spring容器中获得FreeMarkerConfigurer对象。
        // 2、从FreeMarkerConfigurer对象中获得Configuration对象。
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        // 3、使用Configuration对象获得Template对象。
        Template template = configuration.getTemplate("hello.ftl"); //模板名称
        // 4、创建数据集
        Map dataModel = new HashMap<>();
        dataModel.put("hello", "1000");
        // 5、创建输出文件的Writer对象。
        Writer out = new FileWriter(new File("D:/下载视频/编程/jEE/就业/【阶段14】宜立方商城综合项目/freemarker/hello.txt"));
        // 6、调用模板对象的process方法，生成文件。
        template.process(dataModel, out);
        // 7、关闭流。
        out.close();
        return "OK";
    }
}
