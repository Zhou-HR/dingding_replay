package com.gdiot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdiot.entity.DingDept;
import com.gdiot.mapper.DingDeptMapper;
import com.gdiot.service.DingDeptService;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Service("DingDeptService")
public class DingDeptServiceImpl implements DingDeptService {
    @Autowired
    private DingDeptMapper dingDeptMapper;

    @Override
    public void insertDingDept(DingDept dingDept) {
        String depId = dingDept.getDeptId();
        // 查询是否有，有的话替换，无的话插入
        DingDept Dept = dingDeptMapper.selectOne(depId);
        if (Dept != null) {
            // 已存在
            dingDeptMapper.update(dingDept);
        } else {
            // 不存在
            dingDeptMapper.insert(dingDept);
        }
    }

    @Override
    public DingDept selectOne(String deptId) {
        return dingDeptMapper.selectOne(deptId);
    }
}
