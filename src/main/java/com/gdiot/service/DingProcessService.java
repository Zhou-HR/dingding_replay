package com.gdiot.service;

import com.gdiot.entity.DingProcess;

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
    void insert(DingProcess dingProcess);
}
