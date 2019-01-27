package cn.yanzhongxin.controler;

import cn.yanzhongxin.utils.FastDFSClient;
import cn.yanzhongxin.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author www.yanzhongxin.com
 * 处理图片上传的相关服务
 * @date 2018/11/2 15:36
 */
@Controller
public class PictureServiceControler {
    /*请求的url：/pic/upload
    参数：MultiPartFile uploadFile
    返回值：jsons数据，成功时候
    "error":0,
    "url":image_url
    */
    //从配置文件中，获得图片服务器的ip地址 ${}取配置文件中的数值,不知道为什么总是读取不到
    @Value("http://192.168.25.133/")
    private  String image_server_url;

    @RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody //返回json字符串
    public String fileUpload(MultipartFile uploadFile) {
        try {

            System.out.println(uploadFile==null);
            //1、取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //2、创建一个FastDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("D:\\Program Files\\Maven\\apache-maven-3.5.4-bin\\repository\\repository\\cn\\yanzhongxin\\e3-mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
            //3、执行上传处理D:\Program Files\Maven\apache-maven-3.5.4-bin\repository\repository\cn\yanzhongxin\e3-mall\e3-manager-web\target\classes\conf\client.conf
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //4、拼接返回的url和ip地址，拼装成完整的url
            String url = image_server_url + path;
            //5、返回map
            Map result = new HashMap<>();
            result.put("error", 0);
            result.put("url", url);
            System.out.println(url);
            //如果直接相应Map转乘json，会报错，图片上传失败，但是能上传上去
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            //5、返回map
            Map result = new HashMap<>();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            //如果直接相应Map转乘json，会报错，图片上传失败，但是能上传上去
            return  JsonUtils.objectToJson(result);
        }
    }
}
