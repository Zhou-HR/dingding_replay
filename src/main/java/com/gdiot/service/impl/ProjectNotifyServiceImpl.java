package com.gdiot.service.impl;

import com.gdiot.entity.ProjectNotify;
import com.gdiot.mapper.ProjectNotifyMapper;
import com.gdiot.service.ProjectNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<ProjectNotify> list = projectNotifyMapper.selectOne(userId);
        if (list != null && list.size() > 0) {
            // 已存在
            projectNotifyMapper.update(projectNotify);
        } else {
            // 不存在
            projectNotifyMapper.insert(projectNotify);
        }
    }

    @Override
    public void updateNotify(ProjectNotify projectNotify) {
        projectNotifyMapper.updateNotify(projectNotify);
    }
}
