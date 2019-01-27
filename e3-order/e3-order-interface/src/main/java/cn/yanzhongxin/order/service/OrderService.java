package cn.yanzhongxin.order.service;

import cn.yanzhongxin.order.pojo.OrderInfo;
import cn.yanzhongxin.utils.E3Result;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/17 11:27
 */
public interface OrderService {
    E3Result createorder(OrderInfo orderInfo);
}
