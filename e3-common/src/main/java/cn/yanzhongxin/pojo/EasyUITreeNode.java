package cn.yanzhongxin.pojo;

import java.io.Serializable;

/**
 * @author www.yanzhongxin.com
 * 后台商品分类选择，前端接受的数据格式为json
 * [{"id":1,"text":"分类名称","state":"close"}]
 * @date 2018/11/1 9:26
 */
public class EasyUITreeNode  implements Serializable{
    private long id;
    private String text;
    private String state;

    public EasyUITreeNode() {
    }

    public EasyUITreeNode(long id, String text, String state) {

        this.id = id;
        this.text = text;
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
