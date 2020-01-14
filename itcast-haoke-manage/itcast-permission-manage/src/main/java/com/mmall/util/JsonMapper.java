package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author solang
 * 目的：可以将一个Java类转换成json字符串，也可以将json字符串转换成系统中指定的java类对象
 * @date 2020-01-13 18:01
 */
@Slf4j
public class JsonMapper {

    //初始化全局ObjectMapper,核心转换器就是使用ObjectMapper进行处理
    public static ObjectMapper objectMapper = new ObjectMapper();
    //开始使用前需要做一些变量初始化
    static {
        //序列化时遇到不认识字段应该怎么处理,排除掉为空的字段
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }
    /*
     * 孙延楠  目的：对象转换成字符串
     * @Param: [src] :任何对象
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2020/1/13 18:18
     */
    public static <T> String objTowString(T src){
        if (src == null){
            return null;
        }
        try {
            //如果src就是String的话那么直接将src强制转换成String就可以了，如果不是的话直接调用objectMapper
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (Exception e){
            //这里并没有直接抛出异常来，
            // 原因是src这个对象不为空的时候，我们转换为空了，
            // 那么调用端就知道我们这面转换出错了，是否抛出异常由调用端决定
            log.warn("parse object to string exception, error:{}" ,e);
            return null;
        }
    }
    /*
     * 孙延楠
     * @Param: [src：输入字符串, typeReference：要转换的类型]
     * @Return: T
     * @Author: Administrator
     * @Date: 2020/1/13 18:42
     */
    public static <T> T StringTowObj(String src, TypeReference<T> typeReference){
            if (src == null || typeReference == null){
                return null;
            }
            try {
                //如果当前typeReference的类型是Sting的话，那么就直接返回即可，否则需要调用objectMapper方法
                return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
            } catch (Exception e){
                log.warn("parse string to object exception, string:{}, TypeReference<T>:{}, error:{}", src, typeReference.getType(), e);
                return null;
            }
        }
    }
