package com.gdiot.mapper;

import com.gdiot.entity.DingDept;
import com.gdiot.entity.DingUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Mapper
@Component
public interface DingDeptMapper {
    /**
     * 查询
     *
     * @param depId
     * @return
     */
    List<DingDept> selectOne(String depId);

    /**
     * 更新
     *
     * @param dingDept
     */
    void update(DingDept dingDept);

    /**
     * 增加
     *
     * @param dingDept
     */
    void insert(DingDept dingDept);
}