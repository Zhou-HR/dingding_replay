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
     * @param financeNotify
     * @return void
     * @author ZhouHR
     * @date 2021/01/20 19:18
     */
    void insert(FinanceNotify financeNotify);

    /**
     * 查询
     *
     * @param userId
     * @return java.util.List<com.gdiot.entity.FinanceNotify>
     * @author ZhouHR
     * @date 2021/01/20 19:19
     */
    List<FinanceNotify> selectOne(@Param("userId") String userId);

    /**
     * 修改
     *
     * @param financeNotify
     * @return void
     * @author ZhouHR
     * @date 2021/01/20 19:19
     */
    void update(FinanceNotify financeNotify);

    /**
     * 修改是否通知
     * 
     * @param financeNotify
     * @return void
     * @author ZhouHR
     * @date 2021/01/22 15:14
     */
    void updateNotify(FinanceNotify financeNotify);
}