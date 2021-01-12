package com.gdiot.service;

import com.gdiot.entity.DingUser;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/1/12
 */
public interface DingUserService {
    /**
     * 添加用户
     *
     * @param dingUser
     */
    void insertDingUser(DingUser dingUser);

    /**
     * 查询所有用户id
     *
     * @return
     */
    List<DingUser> selectAllUserId();

    /**
     * 更新用户部门信息
     *
     * @param dingUser
     */
    void updateUserDept(DingUser dingUser);
}
