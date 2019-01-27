package cn.yanzhongxin.cart.service.impl;

import cn.yanzhongxin.cart.service.CartService;
import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.mapper.TbItemMapper;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/16 16:52
 */
@Service
public class CartServiceImpl  implements CartService {

    /*根据用户id获得用户redis数据库中购物车信息*/
    @Override
    public List<TbItem> getCartList(long userid) {
        List<String> jsonList = jedisClient.hvals(REDISZ_CART_PRE + ":" + userid);

        List<TbItem> itemsList= new LinkedList<TbItem>();
        for (String item:jsonList){
            boolean add = itemsList.add(JsonUtils.jsonToPojo(item, TbItem.class));
        }
        return itemsList;
    }

    @Override// 更新购物车中的商品个数
    public E3Result updateCartNum(long userid, long itemid, int num) {
        String item = jedisClient.hget(REDISZ_CART_PRE + ":" + userid, itemid + "");
        TbItem tbItem = JsonUtils.jsonToPojo(item, TbItem.class);
        tbItem.setNum(num);//更新商品个数
        //写回数据库
        jedisClient.hset(REDISZ_CART_PRE+":"+userid,itemid+"",JsonUtils.objectToJson(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result clearCartItems(long userid) {
        //删除购物车
        Long del = jedisClient.del(REDISZ_CART_PRE + ":" + userid);
        return E3Result.ok();
    }

    @Override //删除购物车中的商品
    public E3Result deleteCartItem(long userid, long itemid) {
        jedisClient.hdel(REDISZ_CART_PRE+":"+userid,itemid+"");
        return E3Result.ok();
    }

    //合并购物车
    @Override
    public E3Result mergetCart(long userid, List<TbItem> itemsList) {
        for (TbItem tbItem:itemsList){
            addItemIntoCart(userid,tbItem.getId(),tbItem.getNum());
        }
        return E3Result.ok();
    }

    @Autowired
    JedisClient jedisClient;
    @Value("${REDISZ_CART_PRE}")
    String REDISZ_CART_PRE;
    @Autowired
    TbItemMapper itemMapper;
    @Override
    public E3Result addItemIntoCart(long userid, long itemid, int num) {
        //向redis购物车中添加商品，数据类型为hash
        //1 判断redis中是否有用户需要添加的商品
        String addItem = jedisClient.hget(REDISZ_CART_PRE+":"+userid + "", itemid + "");

        if (addItem==null)//说明redis数据库中没有这个商品的缓存
        {
            //根据商品id查询商品信息
            TbItem itemByid = itemMapper.selectByPrimaryKey(itemid);
            itemByid.setNum(num);//补全属性，设置商品的数量
            if (StringUtils.isNotBlank(itemByid.getImage())){
                itemByid.setImage(itemByid.getImage().split(",")[0]);
            }
            //添加这个商品信息到redis
            jedisClient.hset(REDISZ_CART_PRE+":"+userid+"",itemid+"", JsonUtils.objectToJson(itemByid));
            return E3Result.ok();
        }else {
            //更新商品个数
            TbItem tbItem = JsonUtils.jsonToPojo(addItem, TbItem.class);
            tbItem.setNum(tbItem.getNum()+num);
            //写回redis数据库
            jedisClient.hset(REDISZ_CART_PRE+":"+userid+"",itemid+"",JsonUtils.objectToJson(tbItem));
            return E3Result.ok();
        }

    }
}
