package cn.yanzhongxin;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/8 16:51
 */
public class testSolrCloud {
    public static void main(String[] args) throws  Exception{
        queryIndex();
    }

    public static void testSolrCloudAddFileToIndex() throws Exception{
   /*创建CloudSolrServer对象，构造方法是zookeeper的地址列表*/
        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
        //设置默认的Collection属性
        cloudSolrServer.setDefaultCollection("collection2");

        //创建文档对象
        SolrInputDocument document=new SolrInputDocument();

        //向文档对象中添加域
        document.addField("item_title","测试商品啊");
        document.addField("item_price","100");
        document.addField("id","test001");

        //把文档对象写到索引库
        cloudSolrServer.add(document);

        /*提交*/
        cloudSolrServer.commit();
    }


    public static void queryIndex() throws Exception{
        /*创建CloudSolrServer对象，构造方法是zookeeper的地址列表*/
        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
        //设置默认的Collection属性
        cloudSolrServer.setDefaultCollection("collection2");

        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("*:*");

        //执行查询
        QueryResponse query = cloudSolrServer.query(solrQuery);

        //获取查询结果
        SolrDocumentList results = query.getResults();
        System.out.println("总的记录数字："+results.getNumFound());

        for (int i = 0; i < results.size(); i++) {
            System.out.println("查询结果的id:"+  results.get(i).get("id"));
            System.out.println("item_title:"+  results.get(i).get("item_title"));
            System.out.println("item_price:"+  results.get(i).get("item_price"));

        }
    }

}
