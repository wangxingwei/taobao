package cn.yanzhongxin.order.pojo;

import cn.yanzhongxin.pojo.TbOrder;
import cn.yanzhongxin.pojo.TbOrderItem;
import cn.yanzhongxin.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/17 11:23
 */
public class OrderInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
