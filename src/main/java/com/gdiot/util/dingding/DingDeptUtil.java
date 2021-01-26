package com.gdiot.util.dingding;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.gdiot.entity.DingDept;
import com.gdiot.service.DingDeptService;
import com.gdiot.util.SpringContextUtils;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * 钉钉部门工具类
 * 
 * @author ZhouHR
 * @date 2021/01/26 15:04
 **/
public class DingDeptUtil {
    private static DingDeptService dingDeptService;

    /**
     * 获取所有部门的详细信息
     *
     * @param deptId
     * @param accessToken
     * @return com.alibaba.fastjson.JSONArray
     * @author ZhouHR
     * @date 2021/01/20 19:46
     */
    public static JSONArray getAllDeptDetail(String deptId, String accessToken) {
        System.out.println("deptId:" + deptId + "\n");
        if (dingDeptService == null) {
            dingDeptService = SpringContextUtils.getBean(DingDeptService.class);
        }
        // 获取根目录下所有的子部门列表
        List<Long> subDeptList = DingDingUtil.getSubDeptList(deptId, accessToken);
        JSONArray jsonarr = new JSONArray();
        if (subDeptList != null && subDeptList.size() > 0) {
            // 有子部门
            for (int i = 0; i < subDeptList.size(); i++) {
                String deptId0 = String.valueOf(subDeptList.get(i));
                System.out.println("depId0: " + i + ":" + deptId0 + "\n");
                // 获取部门详情
                DingDept dingDept = getDeptDetail(deptId0, accessToken);
                dingDeptService.insertDingDept(dingDept);
                // 获取下一层子部门
                JSONArray subDeptList0 = getAllDeptDetail(deptId0, accessToken);
                if (subDeptList0 != null && subDeptList0.size() > 0) {
                    System.out.println("subDeptList0: " + subDeptList0 + "\n");
                    jsonarr.add(subDeptList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            // 无子部门，获取部门详情
            DingDept dingDept = getDeptDetail(deptId, accessToken);
            dingDeptService.insertDingDept(dingDept);
            return null;
        }
        return jsonarr;
    }

    /**
     * 获取部门详情
     *
     * @param depId
     * @param accessToken
     * @return com.gdiot.entity.DingDept
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static DingDept getDeptDetail(String depId, String accessToken) {
        try {
            // 根据部门id获取部门信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(Long.valueOf(depId));
            OapiV2DepartmentGetResponse rsp = client.execute(req, accessToken);

            System.out.println("getErrcode=" + rsp.getErrcode());

            long errCode = rsp.getErrcode();
            OapiV2DepartmentGetResponse.DeptGetResponse result = rsp.getResult();
            if (errCode == 0) {
                DingDept dingDept = new DingDept();
                dingDept.setDeptId(depId);
                dingDept.setDeptName(result.getName());
                // 父部门id
                dingDept.setParentId(String.valueOf(result.getParentId()));
                // 管理员id
                if (result.getDeptManagerUseridList() != null) {
                    dingDept.setDeptManagerId(result.getDeptManagerUseridList().toString());
                }
                return dingDept;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
