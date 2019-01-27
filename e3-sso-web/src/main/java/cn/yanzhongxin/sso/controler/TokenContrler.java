package cn.yanzhongxin.sso.controler;

import cn.yanzhongxin.sso.service.TokenService;
import cn.yanzhongxin.utils.E3Result;
import cn.yanzhongxin.utils.JsonUtils;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author www.yanzhongxin.com
 * @date 2019/1/10 18:17
 */
@Controller
public class TokenContrler {

    @Autowired
    TokenService tokenService;
    @RequestMapping(value = "/user/token/{token}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        E3Result userInfoByToken = tokenService.getUserInfoByToken(token);

        //相应结果之前，判断是否为jsonp请求，
        if (StringUtils.isNotBlank(callback)){

            return  callback+"("+JsonUtils.objectToJson(userInfoByToken)+");";
        }
        return JsonUtils.objectToJson(userInfoByToken);
    }
}
