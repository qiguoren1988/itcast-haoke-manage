package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //throw new RuntimeException("test exception");
        return JsonData.success("hello, permission");
    }
}
