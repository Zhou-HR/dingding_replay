package com.gdiot.util.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubidRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubidResponse;
import com.gdiot.constant.DingDingConstants;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 钉钉工具类
 * 
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Slf4j
public class DingDingUtil {
    /**
     * 获取Token
     *
     * @return java.lang.String
     * @author ZhouHR
     * @date 2021/01/20 19:46
     */
    public static String getToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingDingConstants.TOKEN_URL);
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(DingDingConstants.APPKEY);
            req.setAppsecret(DingDingConstants.APPSECRET);
            req.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(req);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取子部门列表
     *
     * @param deptId
     * @param accessToken
     * @return java.util.List<java.lang.Long>
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static List<Long> getSubDeptList(String deptId, String accessToken) {
        try {
            // 根据部门id获取部门子部门id
            DingTalkClient client =
                    new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
            OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();

            req.setDeptId(Long.valueOf(deptId));
            // 1 根目录
            OapiV2DepartmentListsubidResponse rsp = client.execute(req, accessToken);
            OapiV2DepartmentListsubidResponse.DeptListSubIdResponse result = rsp.getResult();
            // 子部门id列表
            List<Long> list = result.getDeptIdList();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
