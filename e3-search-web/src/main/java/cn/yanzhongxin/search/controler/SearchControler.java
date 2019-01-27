package cn.yanzhongxin.search.controler;

import cn.yanzhongxin.pojo.SearchResult;
import cn.yanzhongxin.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/6 16:05
 */
@Controller
public class SearchControler {

    @Autowired
    private SearchService searchService;
    //@Value("${PAGE_ROWS}")
    private Integer PAGE_ROWS=60;

    @RequestMapping("/search")
    public String search(String keyword, @RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
        //需要转码
        keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
        //调用Service查询商品信息
        SearchResult result = searchService.search(keyword, page, PAGE_ROWS);
        //把结果传递给jsp页面
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("recourdCount", result.getRecordCount());
        model.addAttribute("page", page);
        model.addAttribute("itemList", result.getItemsList());
        //返回逻辑视图
        return "search";
    }

}
