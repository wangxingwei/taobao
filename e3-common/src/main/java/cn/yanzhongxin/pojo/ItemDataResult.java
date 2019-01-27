package cn.yanzhongxin.pojo;

import java.io.Serializable;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/3 9:45
 */
public class ItemDataResult implements Serializable {
    //加载商品规格
    /*    			$.getJSON('/rest/item/param/item/query/'+data.id,function(_data){
        if(_data && _data.status == 200 && _data.data && _data.data.paramData){
            $("#itemeEditForm .params").show();
            $("#itemeEditForm [name=itemParams]").val(_data.data.paramData);
            $("#itemeEditForm [name=itemParamId]").val(_data.data.id);

        }*/
    private long id;
    private String paramData;

    public ItemDataResult() {
    }

    public ItemDataResult(long id, String paramData) {

        this.id = id;
        this.paramData = paramData;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }
}
