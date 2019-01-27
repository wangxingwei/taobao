package cn.yanzhongxin.service;


import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbItem;
import cn.yanzhongxin.pojo.TbItemDesc;
import cn.yanzhongxin.utils.E3Result;

import java.util.List;


/**
 * 商品管理Service
 */


public interface ItemService {
    /**
     * 根据商品id查询商品信息
     *
     * @param id
     * @return
     */

    public TbItem getItemByid(long id);
    // 根据页号和行号获的数据
    public EasyUIDataGridResult getAllItems(int page, int rows);

    //添加商品的功能,向 tb_item, tb_item_desc表中添加数据
    public E3Result addItemProduct(TbItem tbItem,String desc);

    //商品根据ID删除数据
    public Boolean deleteItemById(long id);

    /*更新商品数据*/
    /*itemService.updateItemById(tbItem);*/
    public Boolean updateItemById(TbItem tbItem);


    //商品下架
    public int instockItem(List<Long> ids, int status);

    /*根据商品id查询商品id*/
    public TbItemDesc getItemDescById(Long itemid);
}