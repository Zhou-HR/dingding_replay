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
    /**
     * 增加
     *
     * @param record
     */
    void insert(DingProcess record);

    /**
     * 查询
     *
     * @param processId
     * @return
     */
    List<DingProcess> selectOne(@Param("processId") String processId);

    /***
     * 更新
     *
     * @param dingProcess
     */
    void update(DingProcess dingProcess);

    /**
     * 查询
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<DingProcess> selectDingProcessAgree(String startTime, String endTime);
}