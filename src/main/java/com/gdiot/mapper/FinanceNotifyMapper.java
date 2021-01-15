package com.gdiot.mapper;

import com.gdiot.entity.DingUser;
import com.gdiot.entity.FinanceNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/14
 */
@Mapper
@Component
public interface FinanceNotifyMapper {
    /**
     * 增加
     *
     * @param financeNotify
     * @return
     */
    void insert(FinanceNotify financeNotify);

    /**
     * 查询
     *
     * @param userId
     * @return
     */
    List<FinanceNotify> selectOne(@Param("userId") String userId);

    /**
     * 更新
     *
     * @param financeNotify
     */
    void update(FinanceNotify financeNotify);
}