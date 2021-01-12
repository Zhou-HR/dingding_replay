package com.gdiot.task;

import com.alibaba.fastjson.JSONArray;
import com.gdiot.redis.RedisUtil;
import com.gdiot.service.AsyncService;
import com.gdiot.util.DingDataAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
public class DataSenderTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSenderTask.class);
    private String data;
    private String type;
    private Map<String, Object> msgMap;

    private AsyncService asyncService;
    public RedisUtil redisUtil;

    public DataSenderTask(String data, String type) {
        super();
        this.data = data;
        this.type = type;
    }

    public DataSenderTask(Map<String, Object> map, String type) {
        super();
        this.msgMap = map;
        this.type = type;
    }

    @Override
    public void run() {
        LOGGER.info("task: DataSenderTask run-data :" + data);
        LOGGER.info("task: DataSenderTask run-type :" + type);
        LOGGER.info("task: DataSenderTask run-msgMap :" + msgMap);

        switch (type) {
            case "all_dept_user_detail":
                getAllDeptUserDetail(data);
                break;
            case "all_dept_detail":
                getAllDeptDetail(data);
                break;
            case "all_user_parent_dept":
                getAllUserParentDept();
                break;
            default:
                break;
        }
    }

    private void getAllDeptUserDetail(String depId) {
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);
        JSONArray userListDetail = mDingDataAnalysis.getAllDeptUserDetail(depId, accessToken);
        LOGGER.info("userListDetail=" + userListDetail);
    }

    private void getAllDeptDetail(String depId) {
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);
        JSONArray userListDetail = mDingDataAnalysis.getAllDeptDetail(depId, accessToken);
        LOGGER.info("userListDetail=" + userListDetail);
    }

    private void getAllUserParentDept() {
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);
        mDingDataAnalysis.getAllUserParentDept(accessToken);
    }

    public AsyncService getAsyncService() {

        return asyncService;
    }

    public void setAsyncService(AsyncService asyncService) {

        this.asyncService = asyncService;
    }

    public RedisUtil getRedisUtil() {

        return redisUtil;
    }

    public void setRedisUtil(RedisUtil redisUtil) {

        this.redisUtil = redisUtil;
    }

}
