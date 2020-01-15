package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author solang
 * 计算level工具类
 * @date 2020-01-14 17:01
 */
public class LevelUtil {
    //定义各个层级之间的分隔符
    public final static String SEPARATOR = "-";
    //定义root ID
    public final static String ROOT = "0";
    /*
     * 孙延楠
     * @Param: [parentLevel：, parentId]
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2020/1/14 17:05
     */
    public static String calculateLevel(String parentLevel, Integer parentId){
        //如果当前parentLevel是空的话，那么返回0
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
