package com.gdiot.dao;

import com.gdiot.entity.DingAccountPo;
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
public interface DingAccountMapper {
    /**
     * @param mDingAccountPo
     * @return
     */
    int insertOne(DingAccountPo mDingAccountPo);

    /**
     * @param client
     * @return
     */
    int countAccountList(@Param("client") String client);

    /**
     * @param client
     * @param limit
     * @param offset
     * @return
     */
    List<DingAccountPo> selectAccountList(@Param("client") String client, @Param("limit") int limit, @Param("offset") int offset);
}
