package com.gdiot.service.impl;

import java.util.List;

import com.gdiot.entity.FinanceNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdiot.entity.ProjectNotify;
import com.gdiot.mapper.ProjectNotifyMapper;
import com.gdiot.service.ProjectNotifyService;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Service("ProjectNotifyService")
public class ProjectNotifyServiceImpl implements ProjectNotifyService {
    @Autowired
    private ProjectNotifyMapper projectNotifyMapper;

    @Override
    public void insertProjectNotify(ProjectNotify projectNotify) {
        // 查询是否有，有的话替换，无的话插入
        String userId = projectNotify.getUserId();
        ProjectNotify notify = projectNotifyMapper.selectOne(userId);
        if (notify != null) {
            // 已存在
            projectNotifyMapper.update(projectNotify);
        } else {
            // 不存在
            projectNotifyMapper.insert(projectNotify);
        }
    }

    @Override
    public List<ProjectNotify> getProjectNotifyList() {
        List<ProjectNotify> projectNotifyList = projectNotifyMapper.selectAll();
        return projectNotifyList;
    }

    @Override
    public void updateNotify(String userId) throws Exception {
        ProjectNotify notify = projectNotifyMapper.selectOne(userId);
        if (notify != null) {
            // 存在
            ProjectNotify projectNotify = null;
            projectNotify.setUserId(userId);
            projectNotify.setNotify(1);
            projectNotifyMapper.updateNotify(projectNotify);
        } else {
            // 不存在
            throw new Exception("用户不存在");
        }
    }
}
