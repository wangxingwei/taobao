package cn.yanzhongxin.search.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author www.yanzhongxin.com
 * 处理全局异常,需要配置一个bean
 * @date 2019/1/8 19:25
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {


    /*sl4j日志输出*/
    Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        //写日志文件
        logger.error("系统发生异常", ex);

        //发邮件、发短信
        //Jmail：可以查找相关的资料
        //需要在购买短信。调用第三方接口即可。
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "系统发生异常，请稍后重试");
        modelAndView.setViewName("error/exception");//返回错误页面
        return modelAndView;
    }

}
