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
    public void insert(DingProcess dingProcess) {
        String processId = dingProcess.getProcessId();
        List<DingProcess> list = dingProcessMapper.selectOne(processId);
        if (list != null && list.size() > 0) {
            dingProcessMapper.update(dingProcess);
        } else {
            dingProcessMapper.insert(dingProcess);
        }
    }
}
