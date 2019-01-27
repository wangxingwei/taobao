package cn.yanzhongxin.search.message;


import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/* @Author Zhongxin Yan
 * @Description To Do 这里是activemq 消息队列queue的消费者，因为在e3-managger中修改商品后就会
 * 作为消息的生产者，进行通知消费者，搜索服务进行更新索引库.这里是接收消息
 * @Date 2019/1/8 20:22
 * @Param 
 * @return 
 */
public class MyMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        TextMessage message1 = (TextMessage) message;
        try {
            String text = message1.getText();
            System.out.println(text);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
