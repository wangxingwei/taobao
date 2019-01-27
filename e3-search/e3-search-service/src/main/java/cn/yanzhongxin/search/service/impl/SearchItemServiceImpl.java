package cn.yanzhongxin.search.service.impl;


import cn.yanzhongxin.pojo.SearchItem;
import cn.yanzhongxin.search.mapper.ItemMapper;
import cn.yanzhongxin.search.service.SearchItemService;
import cn.yanzhongxin.utils.E3Result;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/* @Author Zhongxin Yan
 * @Description To Do 索引库维护
 * @Date 2019/1/6 14:16
 * @Param 
 * @return 
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    ItemMapper itemMapper;//直接调用dao层的类,注入接口

    @Autowired
    SolrServer solrServer;
    @Override
    public E3Result insertAllItemSearch() {
        try {
            //查询商品列表
            List<SearchItem> itemList = itemMapper.getItemList();
            //导入索引库
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                //写入索引库
                solrServer.add(document);
            }
            //提交
            solrServer.commit();
            //返回成功
            return E3Result.ok();

        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500, "商品导入失败");
        }

    }
}
