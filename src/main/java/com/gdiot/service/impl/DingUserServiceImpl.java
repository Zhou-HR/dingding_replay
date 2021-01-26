package com.gdiot.service.impl;

import com.gdiot.entity.DingUser;
import com.gdiot.mapper.DingUserMapper;
import com.gdiot.service.DingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Service("DingUserService")
public class DingUserServiceImpl implements DingUserService {
    @Autowired
    private DingUserMapper dingUserMapper;

    @Override
    public void insertDingUser(DingUser dingUser) {
        // 查询是否有，有的话替换，无的话插入
        String userId = dingUser.getUserId();
        DingUser user = dingUserMapper.selectOne(userId);
        if (user != null) {
            // 已存在
            dingUserMapper.update(dingUser);
        } else {
            // 不存在
            dingUserMapper.insert(dingUser);
        }
    }

    @Override
    public List<DingUser> selectAllUserId() {
        // 查询所有用户id
        return dingUserMapper.selectAllUserId();
    }

    @Override
    public DingUser selectOne(String userId) {
        return dingUserMapper.selectOne(userId);
    }
}
