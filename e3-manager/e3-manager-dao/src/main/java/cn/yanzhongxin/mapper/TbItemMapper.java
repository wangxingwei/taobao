package cn.yanzhongxin.mapper;

import cn.yanzhongxin.pojo.TbItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbItemMapper {


    TbItem selectByPrimaryKey(Long id);

    List<TbItem> getAllItems();
    //插入新的商品
    int insert(TbItem record);

   // 删除商品
    boolean deleteItemById(long id);

    /*更新商品数据*/
    boolean updateItemById(TbItem tbItem);

    /*批量上架下架更新数据*/
    int updateItemInstock(@Param("ids") List<Long> ids,@Param("status") byte b);
}