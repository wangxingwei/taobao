package cn.yanzhongxin.cart.controler;

import cn.yanzhongxin.cart.service.CartService;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.service.ItemService;
import cn.yanzhongxin.utils.CookieUtils;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * 购物车添加商品
 * @date 2019/1/10 19:46
 */
@Controller
public class CartControler {
    @Autowired
    ItemService itemService;
    @Value("${COOKIE_CART_EXPIRE}")
    Integer COOKIE_CART_EXPIRE;

    @Autowired
    CartService cartService;//注入购物车服务
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletResponse response, HttpServletRequest request){

        //首先从request中判断，用户是否登陆。如果登陆把购物车中数据放到redis
        TbUser user = (TbUser)request.getAttribute("user");
        if (user!=null){
            //如果request非空的话，说明用户已经登陆
            //把购物车中的数据添加到到redis中
            cartService.addItemIntoCart(user.getId(), itemId, num);
            return "cartSuccess";
        }


        //从购物车中取出购物车列表
        List<TbItem> cartListFromCookie = getCartListFromCookie(request);
        //判断当前商品是否在购物车内部，如果在的话，就把商品购物数目相加
        boolean flag=false;
        for (int i = 0; i < cartListFromCookie.size(); i++) {
            TbItem tbItem =  cartListFromCookie.get(i);
            if (tbItem.getId()==itemId.longValue()){
                tbItem.setNum(tbItem.getNum()+num);//加上原来的商品数目
                flag=true;
                break;
            }
        }
        if (!flag){//没找到商品，根据商品id,查询商品信息
            TbItem itemByid = itemService.getItemByid(itemId);
            itemByid.setNum(num);
            String image = itemByid.getImage();
            if (StringUtils.isNotBlank(image)){
                itemByid.setImage(image.split(",")[0]);
            }
            cartListFromCookie.add(itemByid);//把商品添加到list中
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartListFromCookie),COOKIE_CART_EXPIRE,true);

        return "cartSuccess";

    }
    private  List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String cart = CookieUtils.getCookieValue(request, "cart", "UTF-8");
        if (StringUtils.isBlank(cart)){
            //返回null
            return  new ArrayList<>();
        }else { //如果非空，说明购物车中有商品
            List<TbItem> tbItems = JsonUtils.jsonToList(cart, TbItem.class);

            return tbItems;
        }
    }

    //展示购物车列表
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request,HttpServletResponse response){
        //判断用户是否为登录状态，如果是的话，从cookie中取出商品数据，和redis中数据进行合并，删除cookie
        //从cookie取出购物车列表
        List<TbItem> cartListFromCookie1 = getCartListFromCookie(request);
        TbUser user = (TbUser)request.getAttribute("user");
        //如果没有登陆只是展示cookie中商品
        if (user!=null){ //非空进行合并
            E3Result e3Result = cartService.mergetCart(user.getId(), cartListFromCookie1);
            CookieUtils.deleteCookie(request,response,"cart"); //根据request删除客户端的cookie
            //从服务端取出购物车列表
            cartListFromCookie1 = cartService.getCartList(user.getId());

        }
        //没有登陆的话，只展示cookie中的购物车列表
        request.setAttribute("cartList",cartListFromCookie1);
        return "cart";
    }

    //ajax商品数目修改,更新cookie中商品id对应的商品数目
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result qudateNum(@PathVariable Long itemId, @PathVariable Integer num,
                              HttpServletRequest request,HttpServletResponse response){

        //判断用户是否登陆了
        TbUser user = (TbUser) request.getAttribute("user");
        if (user!=null){
            cartService.updateCartNum(user.getId(),itemId,num);//如果登陆的话，需要更新商品的num
            return  E3Result.ok();
        }
        //没登陆的话，就更新cookie中的上品个数
        List<TbItem> cartListFromCookie = getCartListFromCookie(request);
        for (int i = 0; i < cartListFromCookie.size(); i++) {
            TbItem tbItem =  cartListFromCookie.get(i);
            if (tbItem.getId()==itemId.longValue()){
                tbItem.setNum(tbItem.getNum()+num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartListFromCookie),COOKIE_CART_EXPIRE,true);

        return  E3Result.ok();

    }
    /*删除购物车中的数据*/
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItemById(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登陆了
        TbUser user = (TbUser) request.getAttribute("user");
        if (user!=null){
            cartService.deleteCartItem(user.getId(),itemId);//如果登陆的话，删除这个用户的购物车商品中的id
            return  "redirect:/cart/cart.html"; //重定向，直接展示购物车数据
        }

        //从cookie中找到所有的购物车数据
        List<TbItem> cartListFromCookie = getCartListFromCookie(request);

        //需要判断

        //找到对应的商品信息
        for (int i = 0; i < cartListFromCookie.size(); i++) {
            TbItem tbItem =  cartListFromCookie.get(i);
            if (tbItem.getId()==itemId){
                //找到了对应的商品
                cartListFromCookie.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartListFromCookie),COOKIE_CART_EXPIRE,true);
        //返回逻辑视图
        return  "redirect:/cart/cart.html"; //重定向，直接展示购物车数据



    }
}
