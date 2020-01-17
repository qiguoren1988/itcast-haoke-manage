package com.mmall.service.impl;

import com.google.common.base.Preconditions;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.paramException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


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
        // 校验部门名称是否重复
        // 比如更新时，当前param是同一个id的话，id=1的部门名称之前叫做技术部，
        // 改完之后还叫技术部，这个时候就不应该去查它了，此时应通过ID进行一次过滤
        if(checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new paramException("同一层级下存在名称相同的部门");
        }
        //使用建造者模式创建一个当前的部门类
        SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        //组装当前level，parentLevel:上一级别level的值，parentId：
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //指定基本信息的值
        sysDept.setOperator("system");//TODO;
        sysDept.setOperateIp("127.0.0.1");//TODO;
        sysDept.setOperateTime(new Date());//TODO;
        //在sysDeptMapper中如使用insert的话会全量的插入所有字段，使用insertSelective的话默认无值时不进行处理
        sysDeptMapper.insertSelective(sysDept);
    }
    /*
     * 孙延楠
     * @Param: [param：传入参数]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/18 5:10
     */
    @Override
    public void update(DeptParam param) {
        //首先要验证下传入进来的参数是否为空
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new paramException("同一层级下存在相同名称的部门");
        }
        //将要更新的部门取出,             before：之前
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        if (checkExist(param.getParentId(), param.getName(), param.getId())){
            throw new paramException("同一层级下存在相同名称的部门");
        }
        //使用建造者模式创建一个当前的部门类    after：之后
        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .remark(param.getRemark()).build();
        //组装当前level，parentLevel:上一级别level的值，parentId：
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        //指定基本信息的值
        after.setOperator("system-update");//TODO;
        after.setOperateIp("127.0.0.1");//TODO;
        after.setOperateTime(new Date());//TODO;
        updateWithChild(before, after);
    }

    @Transactional
    private void updateWithChild(SysDept before, SysDept after){
        String newLevelPrefix = before.getLevel();
        String oldLevelPrefix = after.getLevel();
        if(!after.getLevel().equals(before.getLevel())){
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)){
                for (SysDept dept: deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
        //接下来需要判断是否需要更新子部门
        //取出新部门level前缀

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
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }
    /*
     * 孙延楠
     * ：根据deptId取出对应对象来，如传入是parentId就是取得上一层的部门层级
     * @Param: [deptId：上一级的部门ID]
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
