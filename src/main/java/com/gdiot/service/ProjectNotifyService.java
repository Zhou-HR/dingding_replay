package com.gdiot.service;

import com.gdiot.entity.ProjectNotify;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public interface ProjectNotifyService {
    /**
     * 添加项目通知人员
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:39
     * @param projectNotify
     * @return void
     */
    void insert(ProjectNotify projectNotify);
}
