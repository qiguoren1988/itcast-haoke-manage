package com.mmall.common;

import com.mmall.exception.PermissionException;
import com.mmall.exception.paramException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author solang
 * 作用：全局异常处理类
 * 原理：SpringExceptionResolver类实现HandlerExceptionResolver接口的时候
 * 如果SpringExceptionResolver类被springboot容器管理的话，那么全局异常在返回的时候，
 * 就会被SpringExceptionResolver这个类捕捉住，所以我们只需要在这个类里实现异常处理逻辑就可以了
 *
 * @date 2020-01-07 17:59
 */
@Configuration
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    /*
     * 孙延楠
     * 作用：处理全局异常
     * @Param: [request: 传入请求, response: 接口返回, handler, ex：截取的全局返回抛出异常]
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: Administrator
     * @Date: 2020/1/7 18:30
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        //根据request获取Url
        String url = request.getRequestURI().toString();
        //自定义ModelAndView返回值
        ModelAndView modelAndView;
        //定义全局异常
        String defaultMsg = "System error";
        //对数据请求和页面加以区别并对请求分别做出处理
        //判断url是数据请求还是页面请求，通过判断后缀是否.json即可
        //endsWith()确定此字符串实例的结尾是否与指定的字符串匹配
        if(url.endsWith(".json")) {
            //什么时候该使用默认的msg（defaultMsg）？什么时候又该使用自己抛出来的自定义msg？
            //只有当抛出来的是自己定义的异常时，我们才认为msg是需要直接给用户的，否则都要使用默认的msg代替
            if (ex instanceof PermissionException || ex instanceof paramException) {
                //将异常提交JsonData返回前台
                JsonData result = JsonData.fail(ex.getMessage());
                //为什么要有个toMap方法？因为ModelAndView类里面，允许你传入viewName的同时还可以传入一个map的值
                //这时候为了保证异常返回结果和正常返回结果一致，所以就将当前的JsonData结果跟之前返回值一样的结果
                modelAndView = new ModelAndView("JsonView", result.toMap());
            } else {
                //如检测后不是自定义异常
                log.error("unknow json exception, url:" + url, ex);
                JsonData result = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("JsonView", result.toMap());
            }
        } else if (url.endsWith(".page")){
                log.error("unknow page exception, url:" + url, ex);
                JsonData result = JsonData.fail(ex.getMessage());
                modelAndView = new ModelAndView("exception" , result.toMap());
            }else {
                log.error("unknow page exception, url:" + url, ex);
                JsonData result = JsonData.fail(defaultMsg);
                modelAndView = new ModelAndView("exception" , result.toMap());
            }
        return modelAndView;
    }
}
