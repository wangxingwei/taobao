/**
 * @author www.yanzhongxin.com
 * @date 2019/1/8 16:31
 */
public class TestSolrCloud {
    public static void main(String[] args) {
        /* //创建CloudSolrServer对象，构造方法是zookeeper的地址列表
        CloudSolrServer cloudSolrServer=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
       //设置默认的Collection属性
        cloudSolrServer.setDefaultCollection("collection2");

        //创建文档对象
        SolrInputDocument  document=new SolrInputDocument();

        //向文档对象中添加域
        document.addField("item_title","测试商品啊");
        document.addField("item_price","100");
        document.addField("id","test001");

        //把文档对象写到索引库
        cloudSolrServer.add(document);

        *//*提交*//*
        cloudSolrServer.commit();*/
    }
}
