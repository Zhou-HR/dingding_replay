package com.gdiot.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gdiot.entity.DingProcess;
import com.gdiot.service.AsyncService;
import com.gdiot.service.DingProcessService;
import com.gdiot.task.DataSenderTask;
import com.gdiot.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@RestController
@RequestMapping("/process")
public class DingProcessController {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private DingProcessService dingProcessService;

    /**
     * 获取开票申请列表
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:23
     * @param params
     * @return java.lang.String
     */
    @RequestMapping("/getAllProcessDetail")
    public String getAllProcessDetail(@RequestBody Map<String, String> params) {
        // 开票申请结束时间
        String endTime = null;
        // 开票申请开始时间
        String startTime = null;
        Map<String, Object> map = new HashMap<>();
        // 传参 开始时间和结束时间
        if (params != null && !params.isEmpty()) {
            if (params.containsKey("startTime")) {
                startTime = params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTime = params.get("endTime");
            }
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }

        // 更新开票申请数据库
        DataSenderTask task = new DataSenderTask(map, "all_process_detail");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);

        // 休眠，等待数据库更新完成
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<DingProcess> dingProcessList;
        if (dingProcessService == null) {
            dingProcessService = SpringContextUtils.getBean(DingProcessService.class);
        }
        // 从数据库获取开票申请列表
        dingProcessList = dingProcessService.selectDingProcessAgree(startTime, endTime);
        // List转成json字符串
        String jsonOutput = JSON.toJSONString(dingProcessList, (ValueFilter)(object, ame, value) -> {
            // null值转成""
            if (value == null) {
                return "";
            }
            return value;
        });
        return jsonOutput;
    }

}
