package cn.yanzhongxin.service;

import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 22:50
 */
public interface TbItemDescService {
    //根据商品id,查询商品类
    public TbItemDesc getTbItemDescById(long id);

   /* 更新商品描述信息*/
   public Boolean updateTbItemDescById(TbItem tbItem,String desc);
}
