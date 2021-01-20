package com.gdiot.mapper;

import com.gdiot.entity.ProjectNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Mapper
@Component
public interface ProjectNotifyMapper {
    /**
     * 增加
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @param projectNotify
     * @return void
     */
    void insert(ProjectNotify projectNotify);

    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @param userId
     * @return java.util.List<com.gdiot.entity.ProjectNotify>
     */
    List<ProjectNotify> selectOne(@Param("userId") String userId);

    /**
     * 修改
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @param projectNotify
     * @return void
     */
    void update(ProjectNotify projectNotify);
}