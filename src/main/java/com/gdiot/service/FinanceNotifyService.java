package com.gdiot.service;

import java.util.List;

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
    void insertFinanceNotify(FinanceNotify financeNotify);

    /**
     * 获取财务部门通知人员列表
     *
     * @author ZhouHR
     * @date 2021/01/26 09:45
     * @return void
     */
    List<FinanceNotify> getFinanceNotifyList();

    /**
     * 更新财务通知人员
     *
     * @author ZhouHR
     * @date 2021/01/22 15:12
     * @param userId
     * @return void
     * @throws Exception
     */
    void updateNotify(String userId) throws Exception;
}
