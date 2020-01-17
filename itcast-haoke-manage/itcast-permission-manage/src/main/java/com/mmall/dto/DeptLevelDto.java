package com.mmall.dto;

import com.mmall.model.SysDept;
import lombok.Data;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author solang
 * 作用：适配用的,传入部门对象，copy成本地的DTO
 * @date 2020-01-15 13:28
 */
@Data
public class DeptLevelDto extends SysDept {
    //初始化List里面让其继续包含DeptLevelDto，这样就可以组成一个树形结构
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    //适配方法，当传入SysDept的时候，能直接转换成当前的List<DeptLevelDto>结构
    public static DeptLevelDto adapt(SysDept sysDept){
        //首先声明一个DeptLevelDto
        DeptLevelDto dto = new DeptLevelDto();
        //其次使用copy Bean的工具,能完成两个类相同字段的copy
        BeanUtils.copyProperties(sysDept, dto);
        return dto;
    }
}
