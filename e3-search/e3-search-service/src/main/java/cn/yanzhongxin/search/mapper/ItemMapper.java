package cn.yanzhongxin.search.mapper;


import cn.yanzhongxin.pojo.SearchItem;

import java.util.List;

/* @Author Zhongxin Yan
 * @Description To Do 搜索所有的商品信息
 * @Date 2019/1/6 12:48
 * @Param 
 * @return 
 */
public interface ItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemByID(long itemid);
}
