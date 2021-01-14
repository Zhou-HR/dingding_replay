package com.gdiot.service;

import com.gdiot.entity.DingProcess;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/1/12
 */
public interface DingProcessService {
    /**
     * 插入开票审批数据
     *
     * @param dingProcess
     */
    void insertDingProcess(DingProcess dingProcess);

    /**
     * 查询通过的开票申请
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<DingProcess> selectDingProcessAgree(String startTime, String endTime);
}
