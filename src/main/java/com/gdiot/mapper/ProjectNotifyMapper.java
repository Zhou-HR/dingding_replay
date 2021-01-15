package com.gdiot.mapper;

import com.gdiot.entity.ProjectNotify;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ZhouHR
 * @date 2021/01/14
 */
@Mapper
@Component
public interface ProjectNotifyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectNotify record);

    ProjectNotify selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ProjectNotify record);
}