package cn.yanzhongxin.controler;

import cn.yanzhongxin.pojo.EasyUITreeNode;
import cn.yanzhongxin.service.TbItemCatServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author www.yanzhongxin.com
 * 处理商品列表相关的请求
 * @date 2018/11/1 9:48
 */
@Controller
public class ItemCatControler {

    //自动注入商品列表分类的服务层
    @Autowired
    private TbItemCatServcie tbItemCatServcie;

    
    
    /* @Author www.yanzhongxin.com
     * @Description To Do 
     * @Date 2018/11/1 9:51
     * @Param 根据前端传来请求url，/item/cat/list,请求url id。
     * @return  返回List<EasyUiTreeNode>,默认id等于0，最顶层的父类ID
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody //返回前端json数据,Spring框架把List自动解析成json
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0") long id){
        List<EasyUITreeNode> itemCatList = tbItemCatServcie.getItemCatList(id);

        return itemCatList;
    }
}
