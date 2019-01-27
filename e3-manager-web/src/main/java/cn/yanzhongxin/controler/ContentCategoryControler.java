package cn.yanzhongxin.controler;

import cn.yanzhongxin.content.service.ContentCategorySerivce;
import cn.yanzhongxin.pojo.EasyUITreeNode;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品信息分类的前台
 * @author www.yanzhongxin.com
 * @date 2018/11/3 15:23
 */
@Controller
public class ContentCategoryControler {

    @Autowired
    private ContentCategorySerivce contentCategorySerivce;
    /*请求的url：/content/category/list
    请求的参数：id，当前节点的id。第一次请求是没有参数，需要给默认值“0”
    响应数据：List<EasyUITreeNode>（@ResponseBody）
    Json数据。
*/
    @RequestMapping(value = "/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> getTreeNode(@RequestParam(value = "id",defaultValue = "0") long id){
        List<EasyUITreeNode> list = contentCategorySerivce.showTreeNodeByParentId(id);
        return list;
    }


    /*请求的url：/content/category/create
        请求的参数：
        Long parentId
        String name
        响应的结果：
        json数据，E3Result，其中包含一个对象，对象有id属性，新生产的内容分类id
*/
    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result addTreeNode(@RequestParam("parentId")long id,@RequestParam("name") String name){
        E3Result e3Result = contentCategorySerivce.addContentCategory(id, name);
        return e3Result;
    }

    /*请求的url：/content/category/update
    参数：id，当前节点id。name，重命名后的名称。
    业务逻辑：根据id更新记录。*/
    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public E3Result updatName(long id,String name){

        E3Result e3Result = contentCategorySerivce.addContentCategory(id, name);
        return e3Result;
    }
    /*请求的url：/content/category/delete/
    参数：id，当前节点的id。
    响应的数据：json。E3Result。
    业务逻辑：
    1、根据id删除记录。
    2、判断父节点下是否还有子节点，如果没有需要把父节点的isparent改为false
    3、如果删除的是父节点，子节点要级联删除。
*/
    @RequestMapping(value = "/content/category/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteContentCategory(long id){
        E3Result e3Result = contentCategorySerivce.deleteContentById(id);

        return e3Result;
    }


}
