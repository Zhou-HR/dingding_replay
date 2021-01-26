package com.gdiot.mapper;

import com.gdiot.entity.FinanceNotify;
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
     * @return com.gdiot.entity.ProjectNotify
     */
    ProjectNotify selectOne(@Param("userId") String userId);

    /**
     * 查询所有
     *
     * @return java.util.List<com.gdiot.entity.FinanceNotify>
     * @author ZhouHR
     * @date 2021/01/26 09:49
     */
    List<ProjectNotify> selectAll();

    /**
     * 修改
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:26
     * @param projectNotify
     * @return void
     */
    void update(ProjectNotify projectNotify);

    /**
     * 修改是否通知
     * 
     * @author ZhouHR
     * @date 2021/01/22 15:10
     * @param projectNotify
     * @return void
     */
    void updateNotify(ProjectNotify projectNotify);

}