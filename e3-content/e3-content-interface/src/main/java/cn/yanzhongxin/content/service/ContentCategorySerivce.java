package cn.yanzhongxin.content.service;

import cn.yanzhongxin.pojo.EasyUITreeNode;
import cn.yanzhongxin.utils.E3Result;

import java.util.List;

/**
 * 商品内容服务
 * @author www.yanzhongxin.com
 * @date 2018/11/3 15:00
 */
public interface ContentCategorySerivce {


    //根据内容分类父类id,查询子类
    List<EasyUITreeNode> showTreeNodeByParentId(long id);


    //添加新的内容分类
    E3Result addContentCategory(long parentId, String name);

    //修改内容的名称

    E3Result updateContentName(long id,String name);

    //根据id删除数据
    E3Result deleteContentById(long id);
}
