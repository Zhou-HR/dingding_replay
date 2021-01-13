package com.gdiot.mapper;

import com.gdiot.entity.DingUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Mapper
@Component
public interface DingUserMapper {

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    List<DingUser> selectOne(@Param("userId") String userId);

    /**
     * 更新用户信息
     *
     * @param dingUser
     */
    void update(DingUser dingUser);

    /**
     * 增加
     *
     * @param dingUser
     */
    void insert(DingUser dingUser);

    /**
     * 查询所有用户id
     *
     * @return
     */
    List<DingUser> selectAllUserId();
}