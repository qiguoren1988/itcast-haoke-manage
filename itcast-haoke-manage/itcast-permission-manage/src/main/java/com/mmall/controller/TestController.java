package com.mmall.controller;

import com.google.common.collect.Multiset;
import com.mmall.common.JsonData;
import com.mmall.exception.PermissionException;
import com.mmall.exception.paramException;
import com.mmall.param.TestVo;
import com.mmall.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author solang
 * @date 2020-01-08 0:56
 */

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
        //throw new PermissionException("test exception");
        throw new RuntimeException("test exception");
        //return JsonData.success("hello, permission");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validator(TestVo vo) throws paramException {
        log.info("validate");
        //throw new PermissionException("test exception");
        //throw new RuntimeException("test exception");
        Map<String, String> map = BeanValidator.validateObject(vo);
        try {
            if (MapUtils.isNotEmpty(map)){
                for (Map.Entry<String, String> entry:map.entrySet()) {
                    log.info("{}-->>{}", entry.getKey(), entry.getValue());
                }
            }
        }catch (Exception e){

        }
        BeanValidator.check(vo);
        return JsonData.success("hello, validate");
    }
}
