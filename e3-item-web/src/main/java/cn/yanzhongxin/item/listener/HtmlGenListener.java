package cn.yanzhongxin.item.listener;

import cn.yanzhongxin.item.pojo.Item;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author www.yanzhongxin.com,监听商品添加操作，如果添加后就生成静态的页面html
 *
 * 后台e3-managger添加一个商品以后，会利用activemq发送一个topic,这里会监听到topic
 * 于此同时，这个监听器会生成商品对应的静态页面。商品id.html。路径在配置文件中。
 * 此时只需呀启动nginx，球盖nginx的配置文件，后通过localhost:80/静态页面文件名称就可以访问。
 * @date 2019/1/9 20:21
 */
public class HtmlGenListener implements MessageListener {
    @Autowired
    ItemService itemService;
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}") //静态页面的输出路径
    private String HTML_GEN_PATH;
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage message1 = (TextMessage) message;
            String text = message1.getText();
            Long itemid=new Long(text);

            Thread.sleep(1000);
            TbItem itemByid = itemService.getItemByid(itemid);
            Item item=new Item(itemByid);

            TbItemDesc itemDescById = itemService.getItemDescById(itemid);


            Map dada=new HashMap();
            dada.put("item",item);
            dada.put("itemDesc",itemDescById);

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template=configuration.getTemplate("item.ftl");

            Writer out=new FileWriter(HTML_GEN_PATH+text+".html");

            //静态页面的输出路径

            template.process(dada,out);
            out.close();



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
