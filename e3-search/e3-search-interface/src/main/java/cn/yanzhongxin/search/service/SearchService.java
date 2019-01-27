package cn.yanzhongxin.search.service;

import cn.yanzhongxin.pojo.SearchResult;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/6 15:50
 */
public interface SearchService {
    SearchResult search(String keyword,int
                        page,int rows) throws Exception;
}
