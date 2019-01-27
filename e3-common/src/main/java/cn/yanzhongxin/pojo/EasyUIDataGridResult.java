package cn.yanzhongxin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author www.yanzhongxin.com
 * @date 2018/10/30 15:55
 */
public class EasyUIDataGridResult implements Serializable {

    private Long total;
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public EasyUIDataGridResult() {
    }

    public EasyUIDataGridResult(Long total, List<?> rows) {

        this.total = total;
        this.rows = rows;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EasyUIDataGridResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
