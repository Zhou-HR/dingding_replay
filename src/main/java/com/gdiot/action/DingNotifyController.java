package com.gdiot.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gdiot.entity.FinanceNotify;
import com.gdiot.entity.ProjectNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdiot.service.FinanceNotifyService;
import com.gdiot.service.ProjectNotifyService;

import java.util.*;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@RestController
@RequestMapping("/notify")
public class DingNotifyController {
    @Autowired
    private FinanceNotifyService financeNotifyService;
    @Autowired
    private ProjectNotifyService projectNotifyService;

    /**
     * 获取财务部门通知人员列表
     * 
     * @author ZhouHR
     * @date 2021/01/26 09:44
     * @param params
     * @return java.lang.String
     */
    @RequestMapping("getFinanceNotifyList")
    public String getFinanceNotifyList(@RequestBody Map<String, String> params) {
        // 获取财务部门通知人员列表
        List<FinanceNotify> financeNotifyList = financeNotifyService.getFinanceNotifyList();
        // List转成json字符串
        String jsonOutput = JSON.toJSONString(financeNotifyList, (ValueFilter)(object, ame, value) -> {
            // null值转成""
            if (value == null) {
                return "";
            }
            return value;
        });
        return jsonOutput;
    }

    /**
     * 设置财务部门通知人员列表
     * 
     * @author ZhouHR
     * @date 2021/01/26 10:03
     * @param params
     * @return java.lang.String
     */
    @RequestMapping("setFinanceNotifyList")
    public String setFinanceNotifyList(@RequestBody Map<String, String> params) {
        List<String> userIdList = new ArrayList<>();
        // 传参
        if (params != null && !params.isEmpty()) {
            if (params.containsKey("userId")) {
                userIdList = Arrays.asList(params.get("userId").split(","));
            }
        }
        // 更新财务部门通知人员列表
        for (String userId : userIdList) {
            try {
                financeNotifyService.updateNotify(userId);
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    /**
     * 获取项目部门通知人员列表
     *
     * @author ZhouHR
     * @date 2021/01/26 09:48
     * @param params
     * @return java.lang.String
     */
    @RequestMapping("getProjectNotifyList")
    public String getProjectNotifyList(@RequestBody Map<String, String> params) {
        // 获取项目部门通知人员列表
        List<ProjectNotify> projectNotifyList = projectNotifyService.getProjectNotifyList();
        // List转成json字符串
        String jsonOutput = JSON.toJSONString(projectNotifyList, (ValueFilter)(object, ame, value) -> {
            // null值转成""
            if (value == null) {
                return "";
            }
            return value;
        });
        return jsonOutput;
    }

    /**
     * 设置项目部门通知人员列表
     *
     * @author ZhouHR
     * @date 2021/01/26 10:06
     * @param params
     * @return java.lang.String
     */
    @RequestMapping("setProjectNotifyList")
    public String setProjectNotifyList(@RequestBody Map<String, String> params) {
        List<String> userIdList = new ArrayList<>();
        // 传参
        if (params != null && !params.isEmpty()) {
            if (params.containsKey("userId")) {
                userIdList = Arrays.asList(params.get("userId").split(","));
            }
        }
        // 更新财务部门通知人员列表
        for (String userId : userIdList) {
            try {
                projectNotifyService.updateNotify(userId);
            } catch (Exception e) {
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }
}
