package com.mmall.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author solang
 * 需求：项目里面会涉及到数据请求，页面请求，页面请求的话直接返回页面即可
 * 如果是数据请求的话，通常返回其所需要的数据同时还希望通过返回值告诉使用
 * 者当前请求是正常处理的还是异常处理的，一般都使用json返回，所以就需要定
 * 义一个类，作为json格式返回的结构
 *
 * 注意：当拿到一个正常返回的JsonData对象的时候，首先需要判断ret是True？还是false？？
 * 是True的话代表当前请求是正常处理过的，如果你需要从接口中获取数据，那么在保证ret=True的时候，在后台去对象里面的data数据
 * 是false的话会通过msg来告诉前台使用者后端因为什么原因处理错误了，比如更新时因为权限不够，就能通过msg告诉你没有权限进行操作
 * @date 2020-01-07 17:21
 */

@Getter
@Setter
public class JsonData {

    //返回结果
    private boolean ret;
    //异常返回前台信息
    private  String msg;
    //正常返回前台信息
    private Object data;

    //只传入一个结果
    public JsonData(boolean ret){
        this.ret = ret;
    }

    //成功时，相当ret已经有tree值，只需要给出msg，data即可
    public static JsonData success(Object object, String msg){
        JsonData JsonData = new JsonData(true);
        JsonData.data = object;
        JsonData.msg = msg;
        return JsonData;
    }

    //有时不需要传入msg，因为已经成功了，只需要把成功的数据返回就可以了
    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.data = object;
        return jsonData;
    }

    //有时连成功的数据都不需要返回
    public static JsonData success(){
        JsonData JsonData = new JsonData(true);
        return JsonData;
    }
    //定义失败时候的方法
    public static JsonData fail(String msg){
        JsonData JsonData = new JsonData(false);
        JsonData.msg = msg;
        return JsonData;
    }

    //
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ret", ret);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }
}
