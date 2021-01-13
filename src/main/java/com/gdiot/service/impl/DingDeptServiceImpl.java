package com.gdiot.service.impl;

import com.gdiot.entity.DingDept;
import com.gdiot.mapper.DingDeptMapper;
import com.gdiot.service.DingDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/1/12
 */
@Service("DingDeptService")
public class DingDeptServiceImpl implements DingDeptService {
    @Autowired
    private DingDeptMapper dingDeptMapper;

    @Override
    public void insetDingDept(DingDept dingDept) {
        // 查询是否有，有的话替换，无的话插入
        String depId = dingDept.getDeptId();
        List<DingDept> list = dingDeptMapper.selectOne(depId);
        if (list != null && list.size() > 0) {
            //已存在
            dingDeptMapper.update(dingDept);
        } else {
            //不存在
            dingDeptMapper.insert(dingDept);
        }
    }
}
