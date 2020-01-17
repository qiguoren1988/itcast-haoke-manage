package com.mmall.service;

import com.mmall.dto.DeptLevelDto;

import java.util.List;

/**
 * @author solang
 * @date 2020-01-15 15:09
 */
public interface SysTreeService {
    //返回部门树
    public List<DeptLevelDto> deptTree();
}
