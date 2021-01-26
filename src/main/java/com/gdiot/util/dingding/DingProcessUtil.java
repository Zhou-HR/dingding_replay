package com.gdiot.util.dingding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.request.OapiProcessinstanceListidsRequest;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceListidsResponse;
import com.gdiot.constant.DingDingConstants;
import com.gdiot.entity.DingProcess;
import com.gdiot.entity.DingUser;
import com.gdiot.service.DingProcessService;
import com.gdiot.service.DingUserService;
import com.gdiot.util.DateUtil;
import com.gdiot.util.SpringContextUtils;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉审批工具类
 *
 * @author ZhouHR
 * @date 2021/01/26 15:11
 **/
@Slf4j
public class DingProcessUtil {
    private static DingUserService dingUserService;
    private static DingProcessService dingProcessService;

    /**
     * 获取所有开票审批的列表
     *
     * @param accessToken
     * @param params
     * @return void
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static void getAllProcessDetail(String accessToken, Map<String, Object> params) {
        if (dingUserService == null) {
            dingUserService = SpringContextUtils.getBean(DingUserService.class);
        }
        if (dingProcessService == null) {
            dingProcessService = SpringContextUtils.getBean(DingProcessService.class);
        }

        // 开票申请结束时间
        long endTime = System.currentTimeMillis();
        // 开票申请开始时间
        long startTime = endTime - 24 * 60 * 60 * 1000;
        if (null != params) {
            if (params.containsKey("startTime")) {
                startTime = (long)params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTime = (long)params.get("endTime");
            }
        }
        log.info("startTime-----" + DateUtil.milliSecond2Date(String.valueOf(startTime), "yyyy-MM-dd HH:mm:ss"));
        log.info("endTime-----" + DateUtil.milliSecond2Date(String.valueOf(endTime), "yyyy-MM-dd HH:mm:ss"));
        // 查询所有用户id
        List<DingUser> list = dingUserService.selectAllUserId();
        for (DingUser user : list) {
            String userId = null;
            try {
                userId = user.getUserId();
                System.out.println("userId----" + userId);
            } catch (Exception e) {
                log.info("e=" + e.toString());
            }
            if (userId != null) {
                log.info("userId-----" + userId);
                // 获取开票申请审批id列表
                List<String> processIdList = getProcessListId(startTime, endTime, userId, accessToken);
                log.info("processIdList=" + processIdList.toString());
                if (processIdList != null && processIdList.size() > 0) {
                    for (String processId : processIdList) {
                        DingProcess dingProcess = getProcessInstance(processId, accessToken);
                        if (dingProcess != null) {
                            dingProcessService.insertDingProcess(dingProcess);
                        }
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
    }

    /**
     * 根据用户ID，获取指定时间段开票审批流
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @param accessToken
     * @return java.util.List<java.lang.String>
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static List<String> getProcessListId(long startTime, long endTime, String userId, String accessToken) {
        try {
            // 根据用户ID，获取开票审批数据
            DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
            OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
            req.setProcessCode(DingDingConstants.PROCESS_CODE);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setSize(10L);
            req.setCursor(0L);
            req.setUseridList(userId);
            OapiProcessinstanceListidsResponse rsp = client.execute(req, accessToken);
            List<String> id_list = new ArrayList<>();
            if (rsp != null) {
                long errCode = rsp.getErrcode();
                if (errCode == 0) {
                    log.info("getBody-----" + rsp.getBody() + "\n");
                    OapiProcessinstanceListidsResponse.PageResult result = rsp.getResult();
                    id_list = result.getList();
                    log.info("id_list=" + id_list.toString());
                    if (id_list != null && id_list.size() > 0) {
                        return id_list;
                    }
                }
            }
            return id_list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据实例ID获取审批流
     *
     * @param processId
     * @param accessToken
     * @return com.gdiot.entity.DingProcess
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static DingProcess getProcessInstance(String processId, String accessToken) {
        try {
            // 根据实例ID获取审批数据
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest req = new OapiProcessinstanceGetRequest();
            req.setProcessInstanceId(processId);
            OapiProcessinstanceGetResponse rsp = client.execute(req, accessToken);
            long errCode = rsp.getErrcode();
            log.info("errCode-----" + errCode);

            DingProcess dingProcess;
            if (errCode == 0) {
                // 开票申请审批数据解析
                dingProcess = DingDataAnalysis.AnalysisProcessInstance(processId, rsp);
                return dingProcess;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}
