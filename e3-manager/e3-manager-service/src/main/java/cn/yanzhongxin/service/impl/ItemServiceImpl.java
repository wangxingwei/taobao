package cn.yanzhongxin.service.impl;

import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.mapper.TbItemDescMapper;
import cn.yanzhongxin.mapper.TbItemMapper;
import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.service.ItemService;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.IDUtils;
import cn.yanzhongxin.utils.JsonUtils;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.Date;
import java.util.List;


/**
 * 商品管理Service
 */
@Service
class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    //商品的表述
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    JmsTemplate jmsTemplate; //信息发送的工具类
    @Autowired
    Destination topicDestination;//信息发送的目的地，topic的id

    @Autowired
    JedisClient jedisClient;//用来缓存，用户访问的商品item。通过设置过期时间
    //由Spring进行创建bean, jedisPoolClien,或者jedisClusteClient


    /*#商品数据在缓存中的key前缀
REDIS_ITEM_PRE=ITEM_INFO
#商品数据缓存的过期时间
ITEM_CACHE_EXPIRE=3600*/
    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE; //key的前缀
    @Value("${ITEM_CACHE_EXPIRE}")
    private  Integer ITEM_CACHE_EXPIRE;//缓存过期时间


    /**
     * 根据商品id查询商品信息
     *
     * @param id
     * @return
     */
    @Override
    public TbItem getItemByid(long id) {

        //先查询缓存，如果缓存中有数据直接返回
        try {
            String s = jedisClient.get(REDIS_ITEM_PRE + ":" + id + ":BASE");//获取商品信息
            if (!StringUtils.isEmpty(s)){
                TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);//把缓存中json形式的对象字符串，转换成对象
                return  tbItem;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        TbItem item = itemMapper.selectByPrimaryKey(id);

        try {
            //把查询到的数据缓存到redis中,前缀和过期时间来自于配置文件
            jedisClient.set(REDIS_ITEM_PRE+":"+ id+":BASE",JsonUtils.objectToJson(item));
            jedisClient.expire(REDIS_ITEM_PRE+":"+ id+":BASE",ITEM_CACHE_EXPIRE);
            //设置过期时间

        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }


    /* @Author www.yanzhongxin.com
     * @Description To Do 根据页号page,行号rows获得商品信息
     *
     * @Date 2018/10/30 15:58
     */
    @Override
    public EasyUIDataGridResult getAllItems(int page, int rows) {

        PageHelper.startPage(page, rows);
        List<TbItem> allItems = itemMapper.getAllItems();

        PageInfo pageInfo = new PageInfo(allItems);
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        easyUIDataGridResult.setRows(allItems);

        return easyUIDataGridResult;
    }


    /* @Author www.yanzhongxin.com
     * @Description 添加新的商品到数据库，
     * 添加完成商品信息后，使用activemq消息队列，把新添加的商品id,传递给activemq，通知search系统，进行更新索引库
     * @Date 2018/11/2 18:38
     * @Param 
     * @return 
     */
    @Override
    public E3Result addItemProduct(TbItem tbItem, String desc) {
        //商品id
        final long id = IDUtils.genItemId();
        tbItem.setId(id);
        //补全商品的属性
        tbItem.setStatus((byte) 1); //1 代表正常，2 代表下架 3 代表删除
        Date date = new Date();
        //设置商品的上架时间，更新时间
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        //商品表中添加数据
        itemMapper.insert(tbItem);
        //商品描述类中添加数据
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(date);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setUpdated(date);
        //执行sql插入数据
        tbItemDescMapper.insert(tbItemDesc);

        //发送消息商品id，到activemq消息队列中
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(""+id);
                return message;
            }
        });

        return  E3Result.ok();
    }

    //商品根据id删除数据
    @Override
    public Boolean deleteItemById(long id) {
        boolean b = itemMapper.deleteItemById(id);
        return b;
    }

    /*更新商品数据*/
    @Override
    public Boolean updateItemById(TbItem tbItem) {
        //这里需要补全商品信息 status=#{status},updated=#{updated}
        tbItem.setStatus((byte)1);
        tbItem.setUpdated(new Date());

        boolean b = itemMapper.updateItemById(tbItem);
        return b;
    }

       /*根据商品id查询商品描述*/

    @Override
    public TbItemDesc getItemDescById(Long itemid) {

        //先查询缓存，如果缓存中有数据直接返回
        try {
            String s = jedisClient.get(REDIS_ITEM_PRE + ":" + itemid + ":DESC");//获取商品信息
            if (!StringUtils.isEmpty(s)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(s, TbItemDesc.class);//把缓存中json形式的对象字符串，转换成对象
                return  tbItemDesc;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemid);

        try {
            //把查询到的数据缓存到redis中,前缀和过期时间来自于配置文件
            jedisClient.set(REDIS_ITEM_PRE+":"+ itemid+":DESC",JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(REDIS_ITEM_PRE+":"+ itemid+":DESC",ITEM_CACHE_EXPIRE);
            //设置过期时间

        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItemDesc;
    }

    //商品上架，下架
    @Override
    public int instockItem(List<Long> ids,int status) {
        int i = itemMapper.updateItemInstock(ids, (byte) status);

        return i;
    }




   /* @Override
    //服务层的实现，根据页号，和每页显示个数展示pojo对象
    public EasyUIDataGridResult getItemsList(int page, int rows) {
        //开始设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询 列表中数据
        List<TbItem> tbItems = itemMapper.selectByExample(new TbItemExample());
        //存储到返回json对象中
        EasyUIDataGridResult easyUIDataGridResult=new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(tbItems);

        //通过PageInfo进行包装PageHelper返回的结果
        PageInfo pageInfo=new PageInfo(tbItems);

        easyUIDataGridResult.setTotal(pageInfo.getTotal());

        return easyUIDataGridResult;
    }*/
}