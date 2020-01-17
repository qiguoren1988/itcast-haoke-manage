package com.mmall.param;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author solang
 *  部门参数类
 * @date 2020-01-14 14:48
 */
@Data
public class DeptParam {
    //部门ID，新增不需要，但是修改时需要
    private Integer id;

    //部门名称
    @NotBlank(message = "部门名称不能为空")
    //这里max的值不能大于设计数据库给定的值的上限
    @Length(max = 15, min = 2, message = "部门名称需要在2-15个字符之间")
    private String name;

    //上一级部门的ID，可为空，首层的话为0，所以给个默认值即可
    private Integer parentId = 0;

    //顺序
    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    //备注，可为空
    @Length(max = 150, message = "备注的长度不能大于150个字符长度")
    private String remark;

}
