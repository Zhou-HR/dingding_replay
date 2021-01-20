package com.gdiot.service;

import com.gdiot.entity.FinanceNotify;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface FinanceNotifyService {
    /**
     * 添加财务通知人员
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:39
     * @param financeNotify
     * @return void
     */
    void insert(FinanceNotify financeNotify);
}
