package cn.yanzhongxin.controler;

import cn.yanzhongxin.content.service.ContentService;
import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbContent;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author www.yanzhongxin.com
 *商品内容控制器
 * @date 2018/11/3 19:11
 */
@Controller
public class TbConentControler {
    @Autowired
   private  ContentService contentService;
  /*  请求的url：/content/query/list
    参数：categoryId 分类id
    响应的数据：json数据
    {total:查询结果总数量,rows[{id:1,title:aaa,subtitle:bb,...}]}
    EasyUIDataGridResult
    描述商品数据List<TbContent>
    查询的表：tb_content
    业务逻辑：
    根据内容分类id查询内容列表。要进行分页处理。*/
     @RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
     @ResponseBody
    public EasyUIDataGridResult queryContentList(long categoryId,String page,String rows){
         EasyUIDataGridResult easyUIDataGridResult = contentService.queryListContent(categoryId, page, rows);
        return easyUIDataGridResult;
     }

     /*提交表单请求的url：/content/save
        参数：表单的数据。使用pojo接收TbContent
        返回值：E3Result（json数据）

*/
     @RequestMapping("/content/save")
     @ResponseBody
     public E3Result saveContent(TbContent tbContent){
         E3Result e3Result = contentService.saveContentMessage(tbContent);
         return e3Result;
     }

     /*编辑刚刚的商品信息内容,回显到编辑页面*/
   /* http://localhost:8081/content-edit?_=1541247774138*/
     @RequestMapping("/content-edit")

     public String intoEditPage(){

         return "content-edit";
     }
    //内容编辑
     /*/rest/content/edit*/
    
    
    /* @Author www.yanzhongxin.com
     * @Description 请求url,返回json E3Result数据
     * @Date 2018/11/3 20:44
     * @Param 
     * @return 
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public E3Result contentedit(TbContent tbContent){
        E3Result e3Result = contentService.updateContentById(tbContent);
        return e3Result;
    }

    
    
    /* @Author www.yanzhongxin.com
     * @Description 删除商品内容
     * @Date 2018/11/3 20:45
     * @Param  http://localhost:8081/content/delete
     * @return 
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(long  ids){

        E3Result e3Result = contentService.delteContentById(ids);
        return e3Result;
    }
}
