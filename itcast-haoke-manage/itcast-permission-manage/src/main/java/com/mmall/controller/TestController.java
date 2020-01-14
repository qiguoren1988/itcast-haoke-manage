package com.mmall.controller;

import com.google.common.collect.Multiset;
import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.PermissionException;
import com.mmall.exception.paramException;
import com.mmall.model.SysAclModule;
import com.mmall.param.TestVo;
import com.mmall.util.BeanValidator;
import com.mmall.util.JsonMapper;
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
//        SysAclModuleMapper sysAclModuleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
//        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(1);
//        log.info(JsonMapper.objTowString(sysAclModule));
        BeanValidator.check(vo);
        return JsonData.success("hello, validate");
    }
}
