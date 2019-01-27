package cn.yanzhongxin.controler;

import cn.yanzhongxin.search.service.SearchItemService;
import cn.yanzhongxin.service.ItemService;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/* @Author Zhongxin Yan
 * @Description To Do 提供索引库更新操作
 * @Date 2019/1/6 14:47
 * @Param 
 * @return 
 */
@Controller
public class SearchItemControler {
    @Autowired
    SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result impotItemIndex() {
        E3Result result = searchItemService.insertAllItemSearch();
        return result;
    }

}
