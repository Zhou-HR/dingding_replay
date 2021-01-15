package com.gdiot.mapper;

import com.gdiot.entity.FinanceNotify;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ZhouHR
 * @date 2021/01/14
 */
@Mapper
@Component
public interface FinanceNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceNotify record);

    FinanceNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(FinanceNotify record);
}