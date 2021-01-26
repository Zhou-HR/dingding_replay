package com.gdiot.task;

import com.alibaba.fastjson.JSONArray;
import com.gdiot.redis.RedisUtil;
import com.gdiot.service.AsyncService;
import com.gdiot.util.dingding.DingDeptUtil;
import com.gdiot.util.dingding.DingDingUtil;
import com.gdiot.util.dingding.DingProcessUtil;
import com.gdiot.util.dingding.DingUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
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
            // 通讯录列表解析入库
            case "all_dept_user_detail":
                getAllDeptUserDetail(data);
                break;
            // 部门列表解析入库
            case "all_dept_detail":
                getAllDeptDetail(data);
                break;
            // 开票审批列表解析入库
            case "all_process_detail":
                getAllProcessDetail(msgMap);
                break;
            default:
                break;
        }
    }

    /**
     * 通讯录列表解析入库
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:41
     * @param depId
     * @return void
     */
    private void getAllDeptUserDetail(String depId) {
        // 获取Token
        String accessToken = DingDingUtil.getToken();
        System.out.println("AccessToken=" + accessToken);
        // 获取所有部门下的用户详情，并保存数据库
        JSONArray userListDetail = DingUserUtil.getAllDeptUserDetail(depId, accessToken);
        LOGGER.info("userListDetail=" + userListDetail);
    }

    /**
     * 部门列表解析入库
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:41
     * @param depId
     * @return void
     */
    private void getAllDeptDetail(String depId) {
        // 获取Token
        String accessToken = DingDingUtil.getToken();
        System.out.println("AccessToken=" + accessToken);
        // 获取所有部门的详细信息
        JSONArray userListDetail = DingDeptUtil.getAllDeptDetail(depId, accessToken);
        LOGGER.info("userListDetail=" + userListDetail);
    }

    /**
     * 开票审批列表解析入库
     * 
     * @author ZhouHR
     * @date 2021/01/20 19:42
     * @param map
     * @return void
     */
    private void getAllProcessDetail(Map<String, Object> map) {
        // 获取Token
        String accessToken = DingDingUtil.getToken();
        System.out.println("AccessToken=" + accessToken);
        // 获取所有开票审批的列表
        DingProcessUtil.getAllProcessDetail(accessToken, map);
        LOGGER.info("getAllProcessDetail over");
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
