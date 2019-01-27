package cn.yanzhongxin.utils;

import cn.yanzhongxin.pojo.ItemDataResult;

import java.io.Serializable;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 23:14
 */
public class ItemResult  implements Serializable {
    public ItemResult() {
    }

    public ItemResult(int status, ItemDataResult data) {

        this.status = status;
        this.data = data;
    }

    //加载商品规格
    /*    			$.getJSON('/rest/item/param/item/query/'+data.id,function(_data){
        if(_data && _data.status == 200 && _data.data && _data.data.paramData){
            $("#itemeEditForm .params").show();
            $("#itemeEditForm [name=itemParams]").val(_data.data.paramData);
            $("#itemeEditForm [name=itemParamId]").val(_data.data.id);

        }*/
    private int status;

    private ItemDataResult data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ItemDataResult getData() {
        return data;
    }

    public void setData(ItemDataResult data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ItemResult{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}