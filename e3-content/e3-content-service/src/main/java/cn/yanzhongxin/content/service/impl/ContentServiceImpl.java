package cn.yanzhongxin.content.service.impl;

import cn.yanzhongxin.content.service.ContentService;
import cn.yanzhongxin.jedis.JedisClient;
import cn.yanzhongxin.mapper.TbContentMapper;
import cn.yanzhongxin.pojo.EasyUIDataGridResult;
import cn.yanzhongxin.pojo.TbContent;
import cn.yanzhongxin.pojo.TbContentExample;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/3 19:21
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    TbContentMapper tbContentMapper;

    @Autowired/*注入RedisClient,这个redisClient，
    可能是RedisClientPool,或者是Redis-cluster,由Spring-redis进行控制*/
            JedisClient redisClient;
    /*根据商品id,第几页，以及每页多少数据查询*/

    /*@Value("${CONTENT_LIST}")*/
    String CONTENT_LIST="CONTENT_LIST"; //redis缓存的key
    @Override
    public EasyUIDataGridResult queryListContent(long categoryId, String page, String rows) {
        PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(rows));//开启分页，第page页，每页rows个数据
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
          //查出结果
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);

        PageInfo pageInfo=new PageInfo(tbContents);//PageInfo封装一下
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(tbContents);

        return result;
    }

    //保存内容数据
    /*业务逻辑：
            1、把TbContent对象属性补全。
    categoryId: 116
    title: 1
    subTitle:
    titleDesc:
    url:
    pic:
    pic2:
    content
        2、向tb_content表中插入数据。
            3、返回E3Result*/
    @Override
    public E3Result saveContentMessage(TbContent content) {
        /*增加新的内容列表的时候，需要删除原来缓存的数据*/

        //补全属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入数据
        tbContentMapper.insert(content);
        //只要是涉及到增删改操作，就需要进行缓存同步，如何进行缓存同步呢？
        //一个方式就是通过删除原来的缓存。
        redisClient.hdel(CONTENT_LIST,content.getCategoryId()+"");

        return E3Result.ok();

    }

    //更新原来的内容数据
    @Override
    public E3Result updateContentById(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        int i = tbContentMapper.updateByPrimaryKey(tbContent);
        //只要是涉及到增删改操作，就需要进行缓存同步，如何进行缓存同步呢？
        //一个方式就是通过删除原来的缓存。
        redisClient.hdel(CONTENT_LIST,tbContent.getCategoryId()+"");

        if (i>0){
            return E3Result.ok();
        }else{
            E3Result e3Result=new E3Result();
         e3Result.setStatus(404);
            return e3Result;
        }

    }

    //根据id删除数据
    @Override
    public E3Result delteContentById(long l) {
        int i = tbContentMapper.deleteByPrimaryKey(l);

        if (i>0){
            return E3Result.ok();
        }else{
            E3Result e3Result=new E3Result();
            e3Result.setStatus(404);
            return e3Result;
        }

    }

    
    
    /* @Author www.yanzhongxin.com
     * @Description 根据商品广告分类Id,查询商品内容
     * @Date 2018/11/5 19:33
     * @Param 
     * @return 
     */
    @Override
    public List<TbContent> queryContentsByCategoryId(long cid) {

        /*先查找缓存中有没有数据*/
        try {
            String hget = redisClient.hget(CONTENT_LIST, cid + "");
            if (!com.alibaba.druid.util.StringUtils.isEmpty(hget)){ //判断redis中如果有缓存对应的数据
                List<TbContent> tbContents = JsonUtils.jsonToList(hget, TbContent.class); //把cid对应的json字符串，专成List返回
                return tbContents;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);

        //查询完数据库，需要把数据放到redis缓存中，加快下次访问
        try {
            //以redis数据类型为hash保存到redis中
            redisClient.hset(CONTENT_LIST,cid+"", JsonUtils.objectToJson(tbContents));
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbContents;
    }


}
