package cn.yanzhongxin.controler;

/**
 * @author www.yanzhongxin.com
 * @date 2018/10/25 12:36
 */


import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.service.ItemService;
import cn.yanzhongxin.service.TbItemDescService;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品管理Controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private TbItemDescService tbItemDescService;

    @RequestMapping("/item/{itemId}")
    public String getItemById(@PathVariable long itemId){
        System.out.println("hello word");
        TbItem item=itemService.getItemByid(itemId);
        System.out.println("------"+item.toString());
        return "index";
    }



    
    
    /* @Author www.yanzhongxin.com
     * @Description To Do 
     * @Date 2018/10/30,前端传来页号page,行号rows。我来返回第page叶，rows行数据。以及总行数字。
     * 返回前端，一个json数据，数据格式total:总数
     * @return 
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //调用服务查询商品列表
        EasyUIDataGridResult result = itemService.getAllItems(page,rows);

        return result;
    }

    //商品插入新的数据
    /*请求的url：/item/save
    参数：TbItem item,String desc
    返回值：E3Result*/
    @RequestMapping("/item/save")
    @ResponseBody  //返回json数据
    public E3Result saveNewItem(TbItem tbItem,String desc){
        System.out.println(tbItem);
        E3Result e3Result = itemService.addItemProduct(tbItem, desc);
        return e3Result;
    }

   /* 商品修改商品下架*/

    ///rest/page/item-edit?_=1541156866056
    @RequestMapping(value = "/rest/page/item-edit",method = RequestMethod.GET)///rest/page/item-edit?_=1541161142305
    @ResponseBody
    public TbItem getTbItemForModifyItem(@RequestParam("_") long itemedit){
        TbItem itemByid = itemService.getItemByid(itemedit);

        return  itemByid;
    }


      /*商品修改，负责商品信息回显示。返回E3Result信息，包含状态码和对象数据*/
   @RequestMapping("/rest/item/param/item/query/{id}")
   @ResponseBody
   public E3Result getTbItemByItem(@PathVariable("id") long itemedit){
       try {
           TbItem itemByid = itemService.getItemByid(itemedit);
           E3Result e3Result=new E3Result(itemByid);

           return  e3Result;
       }catch (Exception e){
           E3Result e3Result=new E3Result(400,"400","400");
           return e3Result;
       }


   }


    //商品删除
    @RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteItem( String ids){
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            Integer s = Integer.parseInt(split[i]);
            itemService.deleteItemById(s);
        }
        return E3Result.ok();
    }
      /*商品信息修改，负责把原来的信息覆盖掉,相应返回的json数据E3Result*/
    /*http://localhost:8081/rest/item/update*/

    @RequestMapping(value = "/rest/item/update",method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateItemMessage(TbItem tbItem,String desc){
        //更新商品表
        boolean updatRes=   itemService.updateItemById(tbItem);
        //更新商品描述表
        Boolean aBoolean = tbItemDescService.updateTbItemDescById(tbItem, desc);
        if (updatRes&&aBoolean){
        return E3Result.ok();
     }else {
         return new E3Result(404,"404","404");
     }

    }

    //商品下架功能 http://localhost:8081/rest/item/instock
    /* if (val == 1){
            return '正常';
        } else if(val == 2){
        	return '<span style="color:red;">下架</span>';
        } else {
        	return '未知';
        }*/
    /*前端传来下架商品字符串id，返回前端E3Result*/
    @RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
    @ResponseBody
    public E3Result instockItemByIds(@RequestParam("ids") String lis){
        String[] split = lis.split(",");
        List<Long> list=new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Long.parseLong(split[i]));
            
        }
        int i = itemService.instockItem(list, 2);
        if (i>0){
            return  E3Result.ok();
        }else {
            /* this.status = 200;
            this.msg = "OK";
            this.data = data;*/
            return  new E3Result(404,"下架失败","404");
        }


    }
    /*商品上架 http://localhost:8081/rest/item/reshelf*/
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public E3Result reshelfItemByIds(@RequestParam("ids") String lis){
        String[] split = lis.split(",");
        List<Long> list=new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Long.parseLong(split[i]));

        }
        int i = itemService.instockItem(list, 1);
        if (i>0){
            return  E3Result.ok();
        }else {
            /* this.status = 200;
            this.msg = "OK";
            this.data = data;*/
            return  new E3Result(404,"上架失败","404");
        }


    }
}