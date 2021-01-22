package com.gdiot.service.impl;

import com.gdiot.entity.FinanceNotify;
import com.gdiot.mapper.FinanceNotifyMapper;
import com.gdiot.service.FinanceNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Service("FinanceNotifyService")
public class FinanceNotifyServiceImpl implements FinanceNotifyService {
    @Autowired
    private FinanceNotifyMapper financeNotifyMapper;

    @Override
    public void insertFinanceNotify(FinanceNotify financeNotify) {
        // 查询是否有，有的话替换，无的话插入
        String userId = financeNotify.getUserId();
        List<FinanceNotify> list = financeNotifyMapper.selectOne(userId);
        if (list != null && list.size() > 0) {
            // 已存在
            financeNotifyMapper.update(financeNotify);
        } else {
            // 不存在
            financeNotifyMapper.insert(financeNotify);
        }
    }

    @Override
    public void updateNotify(FinanceNotify financeNotify) {
        financeNotifyMapper.updateNotify(financeNotify);
    }
}
