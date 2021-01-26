package com.gdiot.service;

import com.gdiot.entity.DingDept;
import com.gdiot.entity.DingUser;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface DingDeptService {
    /**
     * 添加部门
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:40
     * @param dingDept
     * @return void
     */
    void insertDingDept(DingDept dingDept);

    /**
     * 根据部门id查询部门
     *
     * @author ZhouHR
     * @date 2021/01/26 13:41
     * @param deptId
     * @return com.gdiot.entity.DingDept
     */
    DingDept selectOne(String deptId);
}
