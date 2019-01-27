package cn.yanzhongxin.content.service.impl;

import cn.yanzhongxin.content.service.ContentCategorySerivce;
import cn.yanzhongxin.mapper.TbContentCategoryMapper;
import cn.yanzhongxin.pojo.EasyUITreeNode;
import cn.yanzhongxin.pojo.TbContentCategory;
import cn.yanzhongxin.pojo.TbContentCategoryExample;
import cn.yanzhongxin.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品内容服务
 * @author www.yanzhongxin.com
 * @date 2018/11/3 15:03
 */
@Service
public class ContentCategorySerivceImpl  implements ContentCategorySerivce{

    //注入内容分类Mapper
    @Autowired
    TbContentCategoryMapper tbContentCategoryMapper;

    //展示商品内容节点
    @Override
    public List<EasyUITreeNode> showTreeNodeByParentId(long id) {
        TbContentCategoryExample tbContentCategoryExample=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //查寻出的结果封装成一个 List<EasyUITreeNode>
        List<EasyUITreeNode> list=new ArrayList<>();
        for(TbContentCategory cent:tbContentCategories){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(cent.getId());
            node.setText(cent.getName());
            node.setState(cent.getIsParent()?"closed":"open");
            list.add(node);
        }
        return list;
    }

    //插入新的商品内容分类
    @Override
    public E3Result addContentCategory(long parentId, String name) {

        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo的属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        //1(正常),2(删除)
        contentCategory.setStatus(1);
        //默认排序就是1
        contentCategory.setSortOrder(1);
        //新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        int insert = tbContentCategoryMapper.insert(contentCategory);
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            //更新到数数据库
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(contentCategory);

    }

    //修改名称
    @Override
    public E3Result updateContentName(long l, String s) {
        TbContentCategory tbContentCategory=new TbContentCategory();
        tbContentCategory.setId(l);
        tbContentCategory.setName(s);
        int i = tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
        if (i>0){
            return E3Result.ok();
        }else{
            E3Result e=new E3Result();
            e.setStatus(404);
            return e;
        }

    }

    //根据Id删除数据
    @Override
    public E3Result deleteContentById(long id) {
        //判断父节点是不是只有这一个节点，如果有的话，把父节点isParent改为false
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = tbContentCategory.getParentId();
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        TbContentCategory tbParent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (list.size()>1){
            //父节点有多个子节点
                //递归删除子节点

            deleteContent(id,tbContentCategoryMapper);

        }else {
            //修改父节点 为false
            tbParent.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(tbParent);
            //递归删除子节点
            deleteContent(id,tbContentCategoryMapper);
        }


        //递归删除文件
        return E3Result.ok();
    }

    public static  void deleteContent( long id,TbContentCategoryMapper tbContentCategoryMapper){
        // 根据这个ID作为查询条件，判断是否为父节点。查询出所有的子节点。在根据子节点查询出所有的子节点。直到
        TbContentCategory content = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (content.getIsParent()){ //如果是父亲节点的话，继续递归
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            for (TbContentCategory tbContentCategory2 : list) {
                deleteContent(tbContentCategory2.getId(),tbContentCategoryMapper);
            }

        }else {
            //删除当前文件
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }
        //删除当前文件夹
        tbContentCategoryMapper.deleteByPrimaryKey(id);
    }
}
