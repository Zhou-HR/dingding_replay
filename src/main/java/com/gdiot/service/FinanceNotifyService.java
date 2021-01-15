package com.gdiot.service;

import com.gdiot.entity.FinanceNotify;

/**
 * @author ZhouHR
 * @date 2021/1/15
 */
public interface FinanceNotifyService {
    /**
     * 添加财务通知人员
     *
     * @param financeNotify
     */
    void insert(FinanceNotify financeNotify);
}
