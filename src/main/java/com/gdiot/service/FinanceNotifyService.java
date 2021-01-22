package com.gdiot.service;

import com.gdiot.entity.FinanceNotify;
import com.gdiot.entity.ProjectNotify;

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
    void insertFinanceNotify(FinanceNotify financeNotify);

   /**更新财务通知人员
    *
    * @author ZhouHR
    * @date 2021/01/22 15:12
    * @param financeNotify
    * @return void
    */
    void updateNotify(FinanceNotify financeNotify);
}
