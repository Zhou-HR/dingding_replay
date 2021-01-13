package com.gdiot.mapper;

import com.gdiot.entity.DingProcess;
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
public interface DingProcessMapper {

    void insert(DingProcess record);

    List<DingProcess> selectOne(@Param("processId") String processId);

    void update(DingProcess dingProcess);
}