package com.gdiot.service;

import java.util.List;

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
     * 获取项目部门通知人员列表
     *
     * @author ZhouHR
     * @date 2021/01/26 09:45
     * @return void
     */
    List<ProjectNotify> getProjectNotifyList();

    /**
     * 更新项目通知人员
     * 
     * @author ZhouHR
     * @date 2021/01/22 15:09
     * @param userId
     * @return void
     * @throws Exception
     */
    void updateNotify(String userId) throws Exception;
}
