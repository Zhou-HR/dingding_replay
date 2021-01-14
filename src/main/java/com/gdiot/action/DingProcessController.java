package com.gdiot.action;

import com.alibaba.fastjson.JSON;
import com.gdiot.entity.DingProcess;
import com.gdiot.service.AsyncService;
import com.gdiot.service.DingProcessService;
import com.gdiot.task.DataSenderTask;
import com.gdiot.util.SpringContextUtils;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ZhouHR
 * @date 2021/1/14
 */
@RestController
@RequestMapping("/process")
public class DingProcessController {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DingProcessController.class);
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private DingProcessService dingProcessService;

    /**
     * 获取开票申请列表
     *
     * @return
     */
    @RequestMapping("/getAllProcessDetail")
    public String getAllProcessDetail(@RequestBody Map<String, String> params) {
        //开票申请结束时间
        String endTime = null;
        //开票申请开始时间
        String startTime = null;
        Map<String, Object> map = new HashMap<>();
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
        //更新开票申请数据库
        DataSenderTask task = new DataSenderTask(map, "all_process_detail");
        task.setAsyncService(asyncService);
        asyncService.executeAsync(task);

        //休眠
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<DingProcess> dingProcessList;
        if (dingProcessService == null) {
            dingProcessService = SpringContextUtils.getBean(DingProcessService.class);
        }
        LOGGER.info("startTime---" + startTime);
        LOGGER.info("endTime---" + endTime);

        dingProcessList = dingProcessService.selectDingProcessAgree(startTime, endTime);

        Gson gson = new Gson();
        String json = gson.toJson(dingProcessList);
        return json;
    }

}
