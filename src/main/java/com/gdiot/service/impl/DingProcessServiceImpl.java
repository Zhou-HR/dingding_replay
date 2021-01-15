package com.gdiot.service.impl;

import com.gdiot.entity.DingProcess;
import com.gdiot.mapper.DingDeptMapper;
import com.gdiot.mapper.DingProcessMapper;
import com.gdiot.service.DingProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/1/12
 */
@Service("DingProcessService")
public class DingProcessServiceImpl implements DingProcessService {
    @Autowired
    private DingProcessMapper dingProcessMapper;

    @Override
    public void insertDingProcess(DingProcess dingProcess) {
        String processId = dingProcess.getProcessId();
        // 查询是否有，有的话替换，无的话插入
        List<DingProcess> list = dingProcessMapper.selectOne(processId);
        if (list != null && list.size() > 0) {
            // 已存在
            dingProcessMapper.update(dingProcess);
        } else {
            // 不存在
            dingProcessMapper.insert(dingProcess);
        }
    }

    @Override
    public List<DingProcess> selectDingProcessAgree(String startTime, String endTime) {
        // 查询通过的开票申请
        return dingProcessMapper.selectDingProcessAgree(startTime, endTime);
    }
}
