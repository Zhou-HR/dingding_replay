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
    void insertProjectNotify(ProjectNotify projectNotify);

    /**
     * 更新项目通知人员
     * 
     * @author ZhouHR
     * @date 2021/01/22 15:09
     * @param projectNotify
     * @return void
     */
    void updateNotify(ProjectNotify projectNotify);
}
