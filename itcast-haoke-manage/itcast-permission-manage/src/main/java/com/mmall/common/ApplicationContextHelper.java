package com.mmall.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author solang
 * 这是一个获取上下文的类,需要让其被spring进行管理
 * @date 2020-01-13 18:57
 */

@Configuration
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {
    //初始化全局ApplicationContext
    private static ApplicationContext applicationContext;

    /*
     * 孙延楠
     * @Param: [context：当系统启动时会将这个类注入到ApplicationContextHelper类里面]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/13 19:00
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //让全局的applicationContext等于context，这样就相当于拿到了一个static的applicationContext
        // 这样需要使用的时候就可以直接使用ApplicationContextHelper下的applicationContext就可以了
        applicationContext = context;
    }

    /*
     * 孙延楠实现从applicationContext里面取上下文里面的spring的bean
     * @Param: [clazz]
     * @Return: T
     * @Author: Administrator
     * @Date: 2020/1/13 19:08
     */
    public static <T> T popBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /*
     * 孙延楠
     * @Param: [name, clazz]
     * @Return: T
     * @Author: Administrator
     * @Date: 2020/1/13 19:09
     */
    public static <T> T popBean(String name, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}