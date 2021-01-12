package com.gdiot.dao;

import com.gdiot.entity.DingDepPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Mapper
@Component
public interface DingDepDataMapper {
    /**
     * @param mDingDepPo
     * @return
     */
    int insertOne(DingDepPo mDingDepPo);

    /**
     * @param mDingDepPo
     * @return
     */
    int updateDetail(DingDepPo mDingDepPo);

    /**
     * @param dep_id
     * @return
     */
    List<DingDepPo> selectOne(@Param("dep_id") String dep_id);
}
