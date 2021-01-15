package com.gdiot.mapper;

import com.gdiot.entity.ProjectNotify;

/**
 * @author ZhouHR
 * @date 2021/01/14
 */
public interface ProjectNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectNotify record);

    ProjectNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ProjectNotify record);
}