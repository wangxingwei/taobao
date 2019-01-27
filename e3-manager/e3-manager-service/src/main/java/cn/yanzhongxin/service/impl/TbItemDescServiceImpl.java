package cn.yanzhongxin.service.impl;

import cn.yanzhongxin.mapper.TbItemDescMapper;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 22:52
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {

    @Autowired
    TbItemDescMapper tbItemDescMapper;
    @Override
    
    
    /* @Author www.yanzhongxin.com
     * @Description To Do 根据商品ID查询商品描述类
     * @Date 2018/11/2 22:55
     * @Param [id]
     * @return cn.yanzhongxin.pojo.TbItemDesc
     */
    public TbItemDesc getTbItemDescById(long id) {
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);

        return tbItemDesc;
    }

    /* 更新商品描述信息*/
    @Override
    public Boolean updateTbItemDescById(TbItem tbItem,String desc) {
        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(null);

        int i = tbItemDescMapper.updateByPrimaryKey(tbItemDesc);

        return i>0?true:false;
    }
}
