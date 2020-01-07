package com.mmall.common;

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
        //判断ex是数据请求还是页面请求

        return null;
    }
}
