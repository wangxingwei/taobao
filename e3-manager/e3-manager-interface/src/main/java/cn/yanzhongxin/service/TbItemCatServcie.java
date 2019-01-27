package cn.yanzhongxin.service;

import cn.yanzhongxin.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/1 9:30
 */
/*后台商品分类选择接口层，定义如何获得商品分类；*/

public interface TbItemCatServcie {
    /*根据商品父类的Id查找数据*/
    List<EasyUITreeNode> getItemCatList(long parentId);
}
