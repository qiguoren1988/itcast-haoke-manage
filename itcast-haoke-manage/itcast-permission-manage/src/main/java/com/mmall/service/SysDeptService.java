package com.mmall.service;

import com.mmall.param.DeptParam;
import org.springframework.stereotype.Service;

/**
 * @author solang
 * @date 2020-01-14 15:03
 */

public interface SysDeptService {
    //新增部门
    public void save(DeptParam param);
}
