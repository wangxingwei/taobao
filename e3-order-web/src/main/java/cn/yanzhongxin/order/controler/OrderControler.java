package cn.yanzhongxin.order.controler;

import cn.yanzhongxin.cart.service.CartService;
import cn.yanzhongxin.order.pojo.OrderInfo;
import cn.yanzhongxin.order.service.OrderService;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbUser;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



/* @Author Zhongxin Yan
 * @Description To Do  能进入到这里，说明拦截器已经保证用户登陆了
 * @Date 2019/1/17 10:58
 * @Param
 * @return
 */
@Controller
public class OrderControler {

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response) {

        //取出用户的id
        TbUser user = (TbUser) request.getAttribute("user");

        //根据用户id查询购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());
        //把购物车列表传递给jsp
        request.setAttribute("cartList", cartList);

        return "order-cart";//返回逻辑视图，包含了用户数据库的购物车列表

        //看到了181页
    }


    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model,HttpServletRequest request){

        //取出用户信息

        TbUser user = (TbUser) request.getAttribute("user");
        //吧用户信息添加到orderinfo
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用订单服务生成订单

        E3Result createorder = orderService.createorder(orderInfo);
        //如果提交成功，删除购物车

        if (createorder.getStatus()==200){
            //插入数据成功，清空购物车
            cartService.clearCartItems(user.getId());
        }

        //把订单号传递给页面

        request.setAttribute("orderId",createorder.getData()); //返回订单id
        //返回总计消费多少钱
        request.setAttribute("payment",orderInfo.getPayment());

        return "success";
    }
}