package com.gdiot.mapper;

import com.gdiot.entity.DingUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Mapper
@Component
public interface DingUserMapper {

    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/26 13:46
     * @param userId
     * @return com.gdiot.entity.DingUser
     */
    DingUser selectOne(@Param("userId") String userId);

    /**
     * 更新
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:25
     * @param dingUser
     * @return void
     */
    void update(DingUser dingUser);

    /**
     * 增加
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @param dingUser
     * @return void
     */
    void insert(DingUser dingUser);

    /**
     * 查询所有
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @return java.util.List<com.gdiot.entity.DingUser>
     */
    List<DingUser> selectAllUserId();
}