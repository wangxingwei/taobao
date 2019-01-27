package cn.yanzhongxin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/6 15:38
 */
public class SearchResult implements Serializable {
    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemsList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<SearchItem> itemsList) {
        this.itemsList = itemsList;
    }
}
