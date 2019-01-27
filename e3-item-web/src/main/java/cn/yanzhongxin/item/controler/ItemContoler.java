package cn.yanzhongxin.item.controler;


import cn.yanzhongxin.item.pojo.Item;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.service.ItemService;
import org.apache.taglibs.standard.tei.ImportTEI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/* @Author Zhongxin Yan
 * @Description To Do 商品详情页面
 * @Date 2019/1/9 16:17
 * @Param 
 * @return 
 */
@Controller
public class ItemContoler {
    @Autowired
    ItemService itemService;
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model){
        //获取商品信息
        TbItem itemByid = itemService.getItemByid(itemId);
        Item item=new Item(itemByid);
        //获取商品描述信息
        TbItemDesc itemDescById = itemService.getItemDescById(itemId);

        //把信息传递给页面
        model.addAttribute("item",item);

        model.addAttribute("itemDesc",itemDescById);

        //返回逻辑视图
        return "item";


    }
}
