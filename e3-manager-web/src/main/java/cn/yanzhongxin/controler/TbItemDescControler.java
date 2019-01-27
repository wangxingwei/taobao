package cn.yanzhongxin.controler;

import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.service.TbItemDescService;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.ItemDescResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 22:57
 */
@Controller
public class TbItemDescControler {
    @Autowired
    TbItemDescService tbItemDescService;

    /*获得商品描述类信息/rest/item/query/item/desc/917770
    * 根据商品id,查询返回商品的描述类，包含信息200,以及商品的标题*/
    /*// 加载商品描述
                    $.getJSON('/rest/item/query/item/desc/'+data.id,function(_data){
        				if(_data.status == 200){
        					//UM.getEditor('itemeEditDescEditor').setContent(_data.data.itemDesc, false);
        					itemEditEditor.html(_data.data.itemDesc);
        				}
        			});*/
    /*商品修改，返回E3Result信息，包含状态码和对象数据，会显商品描述类的信息*/
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public E3Result getTbItemDescByItem(@PathVariable("id") long itemedit) {
        try {
            TbItemDesc tbItemDescById = tbItemDescService.getTbItemDescById(itemedit);
            E3Result e3Result = new E3Result(tbItemDescById);
            return e3Result;



        } catch (Exception e) {
            E3Result e3Result = new E3Result(200,"404","404");
            return e3Result;
        }

    }

}
