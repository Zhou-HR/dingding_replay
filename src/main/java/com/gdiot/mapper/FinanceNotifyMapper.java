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
     * @author ZhouHR
     * @date 2021/01/26 13:48
     * @param userId
     * @return com.gdiot.entity.FinanceNotify
     */
    FinanceNotify selectOne(@Param("userId") String userId);

    /**
     * 查询所有
     * 
     * @return java.util.List<com.gdiot.entity.FinanceNotify>
     * @author ZhouHR
     * @date 2021/01/26 09:49
     */
    List<FinanceNotify> selectAll();

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