package com.mmall.common;

import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author solang
 * 总结：当我定义了全局的HttpInterceptor之后，任何一个请求在请求处理之前都会被preHandle方法进行处理，
 * 之后如果请求正常返回，会被postHandle方法进行处理，然后任何一个请求在其结束的时候，都会触发afterCompletion
 * 这个方法，这个方法是100%会被触发的，postHandle则不一定会被100%会被触发
 * 实际中我们使用HttpInterceptor的时候，
 * 1.用来输出参数，正常所有的请求都以parameterMap方式获取到，但是里面包含的敏感信息则不适合打印到日志中。
 * 因此做参数输出的时候要注意做一次敏感信息过滤
 * 2.监控接口的请求时间，在preHandle接口记一个接口时间，postHandle方法记一个结束时间
 * @date 2020-01-14 12:55
 */
@Slf4j
@Configuration
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";
    /*
     * 孙延楠
     * 作用：在请求准备实现的时候，preHandle放在处理之前的。
     * @Param: [request：从哪来的, response：返回的什么结果, handler：]
     * @Return: boolean
     * @Author: Administrator
     * @Date: 2020/1/14 12:58
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //取出当前请求
        String url = request.getRequestURI().toString();
        //取出当前请求的参数
        Map parameterMap = request.getParameterMap();
        //打印日志
        log.info("request start. url:{}, params:{}", url,JsonMapper.objTowString(parameterMap));
        //获得一个请求开始的时间，并将其放到request里面
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        //请求处理之前的所有操作都是成功的
        return true;
    }
    /*
     * 孙延楠
     * 作用：请求正常结束的时候，会在请求处理完之后调用这个方法
     * @Param: [request, response, handler, modelAndView]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/14 12:59
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //取出当前请求
        String url = request.getRequestURI().toString();
        //当接口处理完的时候，记录一下从进入请求开始，到请求结束所花费的时间
        Long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        //打印日志
        log.info("request finished. url:{}, cost:{}", url, end - start);
    }
    /*
     * 孙延楠
     * 作用：也会在请求结束的时候调用这个方法，postHandle是在正常请求结束的时候会调用
     * 而afterCompletion是在任何请求结束的时候会调用或者异常的情况下
     * @Param: [request, response, handler, ex]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/14 12:59
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //取出当前请求
        String url = request.getRequestURI().toString();
        //当接口处理完的时候，记录一下从进入请求开始，到请求结束所花费的时间
        Long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        //打印日志
        log.info("request completed. url:{}, cost:{}", url, end - start);
    }
}
