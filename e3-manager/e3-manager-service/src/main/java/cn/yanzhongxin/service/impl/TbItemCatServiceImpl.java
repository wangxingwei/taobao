package cn.yanzhongxin.service.impl;

import cn.yanzhongxin.mapper.TbItemCatMapper;
import cn.yanzhongxin.pojo.EasyUITreeNode;
import cn.yanzhongxin.pojo.TbItemCat;
import cn.yanzhongxin.pojo.TbItemCatExample;
import cn.yanzhongxin.service.TbItemCatServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * 新的服务，需要发布注册到Spring中，以及注册到zookeeper中
 * @date 2018/11/1 9:33
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatServcie {

    //注入dao层的，表TbItemcat接口
    @Autowired
    TbItemCatMapper tbItemCatMapper;

    //服务层，根据父类的ID,查找所有的子类数据，放到list中，返回给web层
    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //查询的结果，封装到EasyUITreeNode中
        List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();

//   开始执行dao层的查询
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);

        for (TbItemCat tbItemCat : tbItemCats) {
            easyUITreeNodeList.add(new EasyUITreeNode(tbItemCat.getId(), tbItemCat.getName(), tbItemCat.getIsParent() ? "closed" : "open"));
        }
        return easyUITreeNodeList;

    }
}
