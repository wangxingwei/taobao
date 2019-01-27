package cn.yanzhongxin.utils;

import java.io.Serializable;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/2 23:02
 */
public class ItemDescResult implements Serializable{
    /*$.getJSON('/rest/item/query/item/desc/'+data.id,function(_data){
        				if(_data.status == 200){
        					//UM.getEditor('itemeEditDescEditor').setContent(_data.data.itemDesc, false);
        					itemEditEditor.html(_data.data.itemDesc);
        				}
        			});
            查询返回ItemDescResoult。
        	*/

    private int status;
    private String itemDesc;

    public ItemDescResult(int status, String itemDesc) {
        this.status = status;
        this.itemDesc = itemDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    public static ItemDescResult  ok(String desc){
       return new ItemDescResult(200,desc);
    }
}
