import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/6 13:04
 */
public class testSolj {
    public static void main(String[] args) throws  Exception{
        testSolrCloud();

       /* // 第一步：把solrJ的jar包添加到工程中。
        // 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        // 第三步：创建一个文档对象SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();
        // 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
        document.addField("id", "test001");
        document.addField("item_title", "测试商品");
        document.addField("item_price", "199");
        // 第五步：把文档添加到索引库中。
        solrServer.add(document);
        // 第六步：提交。
        solrServer.commit();*/
    }
    public void deleteDocumentById() throws Exception {
        // 第一步：创建一个SolrServer对象。
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr/collection1");
        // 第二步：调用SolrServer对象的根据id删除的方法。
        solrServer.deleteById("test001");
        // 第三步：提交。
        solrServer.commit();
    }

    public static void testSolrCloud() throws  Exception{

    }


}

