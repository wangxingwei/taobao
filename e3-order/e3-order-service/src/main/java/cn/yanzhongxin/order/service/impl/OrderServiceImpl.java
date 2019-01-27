package cn.yanzhongxin.order.service.impl;

import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.mapper.TbOrderItemMapper;
import cn.yanzhongxin.mapper.TbOrderMapper;
import cn.yanzhongxin.mapper.TbOrderShippingMapper;
import cn.yanzhongxin.order.pojo.OrderInfo;
import cn.yanzhongxin.order.service.OrderService;
import cn.yanzhongxin.pojo.TbOrderItem;
import cn.yanzhongxin.pojo.TbOrderShipping;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/17 11:27
 */
@Service
public class OrderServiceImpl implements OrderService {
    //生成订单
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    JedisClient jedisClient;

    @Value("${ORDER_ID_GEN_KEY}")
    private  String ORDER_ID_GEN_KEY;

    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;

    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;
    @Override
    public E3Result createorder(OrderInfo orderInfo) {

        //生成订单号,利用redis生成订单

        if (!jedisClient.exists(ORDER_ID_GEN_KEY)){
            //如果存在了订单号，就设置为初始值
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_ID_START);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //补全orderinfo的属性
        orderInfo.setOrderId(orderId);
        ////1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);

        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderMapper.insert(orderInfo);
        //向订单明细表中插入数据

        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem:orderItems){
            //生成明细id
            String odId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            //补全pojo属性
            tbOrderItem.setId(odId);
            tbOrderItem.setOrderId(orderId);

            //向明细表中插入数据
            orderItemMapper.insert(tbOrderItem);
        }
        //向物理订单表中插入数据
        TbOrderShipping orderShipping=orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        //清空购物车


        return  E3Result.ok(orderId);//返回订单号
    }
}
