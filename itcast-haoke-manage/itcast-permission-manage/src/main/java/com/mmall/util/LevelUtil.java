package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author solang
 * 计算level工具类
 * @date 2020-01-14 17:01
 */
public class LevelUtil {
    //定义各个层级之间的分隔符
    public final static String SEPARATOR = ".";
    //定义root ID
    public final static String ROOT = "0";
    /*
     * 孙延楠
     * @Param: [parentLevel：上一级level部门层级，其组成其实就是id拼接的, parentId：上一级level部门层级的id]
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2020/1/14 17:05
     */
    public static String calculateLevel(String parentLevel, Integer parentId){
        //如果当前parentLevel是空的话，认为当前是首层，那么返回0
        if (StringUtils.isBlank(parentLevel)){
            return ROOT;
        } else {
            //如果当前parentLevel不是首层的话，假如parentLevel=0-2的话，parentId=1，那么返回  0-2-1
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}
