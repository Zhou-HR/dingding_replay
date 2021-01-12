package com.gdiot.mapper;

import com.gdiot.entity.DingProcess;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Mapper
@Component
public interface DingProcessMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DingProcess record);

    DingProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(DingProcess record);
}