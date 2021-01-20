package com.gdiot.service;

import com.gdiot.entity.DingProcess;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface DingProcessService {
    /**
     * 插入开票审批数据
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:41
     * @param dingProcess
     * @return void
     */
    void insertDingProcess(DingProcess dingProcess);

    /**
     * 查询通过的开票申请
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:41
     * @param startTime
     * @param endTime
     * @return java.util.List<com.gdiot.entity.DingProcess>
     */
    List<DingProcess> selectDingProcessAgree(String startTime, String endTime);
}
