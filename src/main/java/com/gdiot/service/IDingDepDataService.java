package com.gdiot.service;

import com.gdiot.entity.DingDepPo;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public interface IDingDepDataService {
    /**
     * @param mDingDepPo
     * @return
     */
    int addOne(DingDepPo mDingDepPo);

    /**
     * @param dep_id
     * @return
     */
    List<DingDepPo> selectOne(String dep_id);
}
