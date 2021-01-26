package com.gdiot.service;

import com.gdiot.entity.DingUser;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface DingUserService {
    /**
     * 添加用户
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:40
     * @param dingUser
     * @return void
     */
    void insertDingUser(DingUser dingUser);

    /**
     * 查询所有用户id
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:40
     * @return java.util.List<com.gdiot.entity.DingUser>
     */
    List<DingUser> selectAllUserId();

    /**
     * 根据用户id查询用户
     * 
     * @author ZhouHR
     * @date 2021/01/26 13:41
     * @param userId
     * @return com.gdiot.entity.DingUser
     */
    DingUser selectOne(String userId);
}
