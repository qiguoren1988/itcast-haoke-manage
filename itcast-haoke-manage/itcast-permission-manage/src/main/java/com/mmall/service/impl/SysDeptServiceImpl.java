package com.mmall.service.impl;

import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.paramException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author solang
 * @date 2020-01-14 15:29
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    /*
     * 孙延楠
     * @Param: [param：传入参数]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/14 15:32
     */
    @Override
    public void save(DeptParam param) {
        //参数验证
        BeanValidator.check(param);
        //校验部门名称是否重复
        // 比如更新时，当前param是同一个id的话，id=1的部门名称之前叫做技术部，
        // 改完之后还叫技术部，这个时候就不应该去查它了，此时应通过ID进行一次过滤
        if(checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new paramException("同一层级下存在名称相同的部门");
        }
        //组装当前level
        SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        //parentLevel:上一级别level的值，parentId：
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //指定基本信息的值
        sysDept.setOperator("system");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());
        //在sysDeptMapper中如使用insert的话会全量的插入所有字段，使用insertSelective的话默认无值时不进行处理
        sysDeptMapper.insertSelective(sysDept);
    }
    /*
     * 孙延楠
     * 作用：同一级部门下面不能出现名称重复的部门
     * @Param: [parentId：如果部门相同那么parentId应该是一致的, deptName：部门名称, deptId：更新的时候需要检查]
     * @Return: boolean
     * @Author: Administrator
     * @Date: 2020/1/14 15:36
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){

        return true;
    }
    /*
     * 孙延楠
     * ：根据deptId取出对应对象来，如传入是parentId就是取得上一层的部门
     * @Param: [deptId：部门ID]
     * @Return: java.lang.String
     * @Author: Administrator
     * @Date: 2020/1/14 17:29
     */
    private String getLevel(Integer deptId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        //如果为空，则可能是0这种值，此时需直接返回Null
        if (sysDept == null){
            return null;
        }
        return sysDept.getLevel();
    }
}
