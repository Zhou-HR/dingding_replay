package com.gdiot.mapper;

import com.gdiot.entity.DingProcess;
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
public interface DingProcessMapper {
    /**
     * 增加
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:24
     * @param record
     * @return void
     */
    void insert(DingProcess record);

    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:25
     * @param processId
     * @return java.util.List<com.gdiot.entity.DingProcess>
     */
    List<DingProcess> selectOne(@Param("processId") String processId);

    /**
     * 更新
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:25
     * @param dingProcess
     * @return void
     */
    void update(DingProcess dingProcess);

    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:25
     * @param startTime
     * @param endTime
     * @return java.util.List<com.gdiot.entity.DingProcess>
     */
    List<DingProcess> selectDingProcessAgree(String startTime, String endTime);
}