package cn.yanzhongxin.cart.service;

import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.utils.E3Result;

import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/16 16:50,
 */
public interface CartService {
    //把用户id作为redis的key,商品id作为key,商品详细信息作为value
    E3Result addItemIntoCart(long userid,long itemid,int num);

    //合并购物车
    E3Result mergetCart(long userid, List<TbItem> itemsList);
    //查找redis购物车中的数据,取购物车列表
    List<TbItem> getCartList(long userid);

    E3Result updateCartNum(long userid,long itemid,int num);//更新购物车商品个数

    E3Result deleteCartItem(long userid,long itemid);//删除购物车中的商品


    E3Result clearCartItems(long userid);//清除购物车
}
