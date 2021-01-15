package com.gdiot.service;

import com.gdiot.entity.FinanceNotify;
import com.gdiot.entity.ProjectNotify;

/**
 * @author ZhouHR
 * @date 2021/1/15
 */
public interface ProjectNotifyService {
    /**
     * 添加项目通知人员
     *
     * @param projectNotify
     */
    void insert(ProjectNotify projectNotify);
}
