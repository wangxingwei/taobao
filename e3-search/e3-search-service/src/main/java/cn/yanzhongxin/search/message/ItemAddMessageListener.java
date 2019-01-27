package cn.yanzhongxin.search.message;


import cn.yanzhongxin.pojo.SearchItem;
import cn.yanzhongxin.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/* @Author Zhongxin Yan
 * @Description To Do 这里是activemq 消息队列queue的消费者，因为在e3-managger中修改商品后就会
 * 作为消息的生产者，进行通知消费者，搜索服务进行更新索引库.监听商品添加事件
 * 把对应的商品信息，添加到索引库
 * @Date 2019/1/8 20:22
 * @Param 
 * @return 
 */
public class ItemAddMessageListener implements MessageListener{
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    SolrServer solrServer;
    @Override
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage) message;
        try {
            /*搜索刚插入的商品列表信息，添加到索引库中*/
            String text = message1.getText();
            Long id=new Long(text);
            SearchItem searchItem = itemMapper.getItemByID(id);//新插入的商品
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
            solrServer.commit();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
