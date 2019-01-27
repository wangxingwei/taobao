package cn.yanzhongxin.content.service;

import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbContent;
import cn.yanzhongxin.utils.E3Result;

import java.util.List;

/**
 * @author www.yanzhongxin.com
 * 商品内容
 * @date 2018/11/3 19:19
 */
public interface ContentService {
    //根据id查询出商品的内容
    public EasyUIDataGridResult queryListContent(long pardentId,String pages,String rows);

    //保存内容数据
     public E3Result saveContentMessage(TbContent tbContent);

     //编辑更新内容数据
    public E3Result updateContentById(TbContent tbContent);


    //删除商品内容数据
    public E3Result delteContentById(long ids);

    //根据商品内容分类id，查询广告内容

    public List<TbContent> queryContentsByCategoryId(long cid);
}
