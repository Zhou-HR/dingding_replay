package com.gdiot.mapper;

import com.gdiot.entity.DingDept;
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
public interface DingDeptMapper {
    /**
     * 查询
     * 
     * @author ZhouHR
     * @date 2021/01/26 13:45
     * @param deptId
     * @return com.gdiot.entity.DingDept
     */
    DingDept selectOne(@Param("deptId") String deptId);

    /**
     * 更新
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:24
     * @param dingDept
     * @return void
     */
    void update(DingDept dingDept);

    /**
     * 增加
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:24
     * @param dingDept
     * @return void
     */
    void insert(DingDept dingDept);
}