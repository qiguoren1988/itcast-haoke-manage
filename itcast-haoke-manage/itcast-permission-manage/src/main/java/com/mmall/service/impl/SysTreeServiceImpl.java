package com.mmall.service.impl;

import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysDept;
import com.mmall.service.SysTreeService;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.shaded.com.google.common.collect.ArrayListMultimap;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.curator.shaded.com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author solang
 * @date 2020-01-15 15:11
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    /*
     * 孙延楠
     * 作用：返回部门树List<DeptLevelDto>
     * @Param: []
     * @Return: java.util.List<com.mmall.dto.DeptLevelDto>
     * @Author: Administrator
     * @Date: 2020/1/15 15:12
     */
    @Override
    public List<DeptLevelDto> deptTree() {
        //首先取出部门列表
        List<SysDept> deptList = sysDeptMapper.getAllDept();
        //将其适配成List<DeptLevelDto>
        List<DeptLevelDto> dtoList = Lists.newArrayList();
        //遍历deptList将其适配成dto然后存入List<DeptLevelDto>中
        for (SysDept sysDept : deptList){
            DeptLevelDto dto = DeptLevelDto.adapt(sysDept);
            dtoList.add(dto);
        }
        //将List<DeptLevelDto>做成树形结构，其实就是把每个dto里面的deptList列出来就可以了
        return deptListToTree(dtoList);
    }
    /*
     * 孙延楠
     * 作用：要不停的递归将tree组装起来
     * @Param: [dtoList]
     * @Return: java.util.List<com.mmall.dto.DeptLevelDto>
     * @Author: Administrator
     * @Date: 2020/1/15 15:35
     */
    public List<DeptLevelDto> deptListToTree (List<DeptLevelDto> DeptLevelList){
        //如果当前deptLevelList为空值，则返回一个普通的list回去
        if(CollectionUtils.isEmpty(DeptLevelList)){
            return Lists.newArrayList();
        }
        //定义一个特殊的数据结构，将其当前的Tree以它的Level为Key
        //它的相同Level的部门做成它的Value，放到Map里面去，，，level--》【dept1，dept2....】
        //也就是说level的key对应的value其实是一个list，因为list每一个结构都是detp对应的dto
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        //首先初始化一个list，里面数据是一级部门名称也就是ROOT=0的那一级
        List<DeptLevelDto> rootList = Lists.newArrayList();
        for (DeptLevelDto dto : DeptLevelList) {
            //每一个值都将当前level取出来，放入levelDeptMap中去，这样就可以达到每次取level之后
            //都可以将相同level的List取出来，这样当我定义好一个部门level以后，其下面所有部门都放到value里面
            levelDeptMap.put(dto.getLevel(), dto);
            //判断，如果level是0的话，也就是当前是顶级部门的话，就将其加入到rootlist里面去
            if (LevelUtil.ROOT.equals(dto.getLevel())){
                //至此也就完成了数据结构的初始化，接下来需要根据rootList为起点，levelDeptMap作为辅助数据，
                //然后将rootList返回
                rootList.add(dto);
            }
        }
        //对rootList增加排序seq，因为同一级部门是有顺序的，
        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            //按照Seq从小到大排序
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq() - o2.getSeq();
            }
        });
        transformDeptTree(rootList, LevelUtil.ROOT,levelDeptMap);
        return DeptLevelList;
    }
    /*
     * 孙延楠
     * 作用：将当前已经排好序的这一级别的值对它下面的值进行递归排序，每遍历一层level之后都对其进行树形结构的处理
     * @Param: [DeptLevelList：当前的结构, level：当前的level, levelDeptMap]
     * @Return: void
     * @Author: Administrator
     * @Date: 2020/1/17 17:56
     */
    public void transformDeptTree(List<DeptLevelDto> rootList, String level, Multimap<String, DeptLevelDto> levelDeptMap){
        //实现递归
        for (int i = 0; i < rootList.size(); i++) {
            //遍历该层的每个元素
            DeptLevelDto deptLevelDto = rootList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>)levelDeptMap.get(nextLevel);
            //需要对当前层级进行一次排序相关处理
            if (CollectionUtils.isNotEmpty(tempDeptList)){
                //排序
                Collections.sort(tempDeptList, deptSeqComparator);
                //设置下一部门
                deptLevelDto.setDeptList(tempDeptList);
                //进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel,levelDeptMap);
            }
        }
    }

    public Comparator<DeptLevelDto> deptSeqComparator = new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
