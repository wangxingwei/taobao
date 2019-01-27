package cn.yanzhongxin.item.pojo;

import cn.yanzhongxin.pojo.TbItem;



/* @Author Zhongxin Yan
 * @Description 网页jsp需要 item.getImages[0]，但是
 * 我们的TbItem中没有这个方法，只有getImage，但是是用逗号分隔
 * 的字符串
 * @Date 2019/1/9 15:49
 * @Param 
 * @return 
 */
public class Item extends TbItem {
    
    
    /* @Author Zhongxin Yan
     * @Description 对父类进行进一步的包装操作
     * 只需要把image url进行分割成字符串数组
     * @Date 2019/1/9 15:51
     * @Param 
     * @return 
     */
    public String[] getImages() {
        String image2 = this.getImage();
        if (image2 != null && !"".equals(image2)) {
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }

    public Item() {

    }

    public Item(TbItem tbItem) {
        this.setBarcode(tbItem.getBarcode());
        this.setCid(tbItem.getCid());
        this.setCreated(tbItem.getCreated());
        this.setId(tbItem.getId());
        this.setImage(tbItem.getImage());
        this.setNum(tbItem.getNum());
        this.setPrice(tbItem.getPrice());
        this.setSellPoint(tbItem.getSellPoint());
        this.setStatus(tbItem.getStatus());
        this.setTitle(tbItem.getTitle());
        this.setUpdated(tbItem.getUpdated());
    }

}
