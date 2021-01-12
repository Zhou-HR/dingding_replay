package com.gdiot.mapper;

import com.gdiot.entity.DingProcess;

public interface DingProcessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DingProcess record);

    int insertSelective(DingProcess record);

    DingProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DingProcess record);

    int updateByPrimaryKey(DingProcess record);
}