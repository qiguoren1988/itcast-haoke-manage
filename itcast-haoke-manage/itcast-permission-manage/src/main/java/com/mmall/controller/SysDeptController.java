package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.DeptLevelDto;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author solang
 * @date 2020-01-14 14:58
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreeService sysTreeService;
    /*
     * 孙延楠
     * 新增部门
     * @Param: [deptParam]
     * @Return: com.mmall.common.JsonData
     * @Author: Administrator
     * @Date: 2020/1/14 15:01
     */
    @RequestMapping("save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){
        sysDeptService.save(deptParam);
        return JsonData.success();
    }
    /*
     * 孙延楠
     * @Param: []
     * @Return: com.mmall.common.JsonData
     * @Author: Administrator
     * @Date: 2020/1/17 18:39
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }
    /*
     * 孙延楠
     * 注意：一颗部门树形成之后，在其中间进行更新的话，其下面的层级要跟着发生相应的变化
     * @Param: [deptParam]
     * @Return: com.mmall.common.JsonData
     * @Author: Administrator
     * @Date: 2020/1/18 4:58
     */
    @RequestMapping("update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam deptParam){
        sysDeptService.update(deptParam);
        return JsonData.success();
    }
}
