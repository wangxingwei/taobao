package cn.yanzhongxin.portal.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www.yanzhongxin.com
 * @date 2018/11/4 17:25
 */
public class Data {

    /* title: {
     text: 'ECharts 入门示例'
 },
 tooltip: {},
 legend: {
     data:['销量']
 },
 xAxis: {
     data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
 },
 yAxis: {},
 series: [{
     name: '销量',
             type: 'bar',
             data: [5, 20, 36, 10, 10, 20]
 }]*/

   private List<HashMap<String,String>> title;
   private String tooltip;
   private Map legend;
   private Map data;

    public List getTitle() {
        return title;
    }

    public void setTitle(List title) {
        this.title = title;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public Map getLegend() {
        return legend;
    }

    public void setLegend(Map legend) {
        this.legend = legend;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public List getSeries() {
        return series;
    }

    public void setSeries(List series) {
        this.series = series;
    }

    private String yAxis;
   private List series;

}
