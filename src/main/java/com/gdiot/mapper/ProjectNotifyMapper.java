package com.gdiot.mapper;

import com.gdiot.entity.ProjectNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/14
 */
@Mapper
@Component
public interface ProjectNotifyMapper {
    /**
     * 增加
     *
     * @param projectNotify
     */
    void insert(ProjectNotify projectNotify);

    /**
     * 查询
     *
     * @param userId
     * @return
     */
    List<ProjectNotify> selectOne(@Param("userId") String userId);

    /**
     * 修改
     *
     * @param projectNotify
     */
    void update(ProjectNotify projectNotify);
}