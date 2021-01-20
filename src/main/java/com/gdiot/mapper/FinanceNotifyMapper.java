package com.gdiot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.gdiot.entity.FinanceNotify;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:17
 */
@Mapper
@Component
public interface FinanceNotifyMapper {
    /**
     * 添加
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:18
     * @param financeNotify
     * @return void
     */
    void insert(FinanceNotify financeNotify);

    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:19
     * @param userId
     * @return java.util.List<com.gdiot.entity.FinanceNotify>
     */
    List<FinanceNotify> selectOne(@Param("userId") String userId);

    /**
     * 更新
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:19
     * @param financeNotify
     * @return void
     */
    void update(FinanceNotify financeNotify);
}