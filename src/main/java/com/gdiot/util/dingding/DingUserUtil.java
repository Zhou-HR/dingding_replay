package com.gdiot.util.dingding;

import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListidRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.gdiot.entity.DingUser;
import com.gdiot.service.DingUserService;
import com.gdiot.util.SpringContextUtils;
import com.taobao.api.ApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * 钉钉用户工具类
 *
 * @author ZhouHR
 * @date 2021/01/26 14:59
 **/
public class DingUserUtil {
    private static DingUserService dingUserService;

    /**
     * 获取所有部门下的用户详情，并保存数据库
     *
     * @param deptId
     * @param accessToken
     * @return com.alibaba.fastjson.JSONArray
     * @author ZhouHR
     * @date 2021/01/20 19:46
     */
    public static JSONArray getAllDeptUserDetail(String deptId, String accessToken) {
        System.out.println("deptId:" + deptId + "\n");
        // 获取根目录下所有的子部门列表
        List<Long> subDeptList = DingDingUtil.getSubDeptList(deptId, accessToken);
        System.out.println("subDeptList:" + subDeptList + "\n");

        JSONArray jsonarr = new JSONArray();

        if (subDeptList != null && subDeptList.size() > 0) {
            // 有子部门
            for (int i = 0; i < subDeptList.size(); i++) {
                String deptId0 = String.valueOf(subDeptList.get(i));
                // 获取部门下的用户
                getDeptUserDetailSave(deptId0, accessToken);
                // 获取子部门
                JSONArray subDeptList0 = getAllDeptUserDetail(deptId0, accessToken);
                if (subDeptList0 != null && subDeptList0.size() > 0) {
                    jsonarr.add(subDeptList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            // 无子部门，获取部门下用户
            getDeptUserDetailSave(deptId, accessToken);
            return null;
        }
        return jsonarr;
    }

    /**
     * 获取部门下的所有用户详情
     *
     * @param deptId
     * @param accessToken
     * @return void
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static void getDeptUserDetailSave(String deptId, String accessToken) {
        if (dingUserService == null) {
            dingUserService = SpringContextUtils.getBean(DingUserService.class);
        }
        // 获取指定部门ID的所有用户userID列表
        List<String> deptUserList = getDeptUserList(deptId, accessToken);
        System.out.println("deptUserList: " + deptUserList + "\n");
        if (deptUserList != null && deptUserList.size() > 0) {
            // 部门下有用户
            for (String userID0 : deptUserList) {
                // 获取用户详情
                DingUser dingUser = getUserDetail(userID0, accessToken);
                dingUserService.insertDingUser(dingUser);
            }
        } else {
            // 部门下无用户
        }
    }

    /**
     * 获取部门用户列表
     *
     * @param deptId
     * @param accessToken
     * @return java.util.List<java.lang.String>
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static List<String> getDeptUserList(String deptId, String accessToken) {
        try {
            // 获取该部门下所有用户id列表
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
            OapiUserListidRequest req = new OapiUserListidRequest();
            req.setDeptId(Long.valueOf(deptId));
            OapiUserListidResponse rsp = client.execute(req, accessToken);

            OapiUserListidResponse.ListUserByDeptResponse result = rsp.getResult();
            // 用户id列表
            List<String> list = result.getUseridList();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @param accessToken
     * @return com.gdiot.entity.DingUser
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static DingUser getUserDetail(String userId, String accessToken) {
        try {
            // 根据用户id获取用户信息
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            OapiV2UserGetResponse rsp = client.execute(req, accessToken);

            System.out.print("getErrcode=" + rsp.getErrcode() + "\n");

            long errCode = rsp.getErrcode();
            OapiV2UserGetResponse.UserGetResponse result = rsp.getResult();
            if (errCode == 0) {
                DingUser dingUser = new DingUser();
                dingUser.setUserId(result.getUserid());
                dingUser.setName(result.getName());
                dingUser.setUnionid(result.getUnionid());
                // 职位
                dingUser.setPosition(result.getTitle());
                dingUser.setTelephone(result.getTelephone());
                dingUser.setMobile(result.getMobile());
                dingUser.setEmail(result.getEmail());
                // 所在部门
                List<Long> deptIdList = result.getDeptIdList();
                switch (deptIdList.size()) {
                    case 1:
                        // 部门1
                        dingUser.setDept1(String.valueOf(deptIdList.get(0)));
                        break;
                    case 2:
                        // 部门2
                        dingUser.setDept1(String.valueOf(deptIdList.get(0)));
                        dingUser.setDept2(String.valueOf(deptIdList.get(1)));
                        break;
                    default:
                        break;
                }
                // 工号
                dingUser.setWorkNo(result.getJobNumber());
                // 入职时间
                dingUser.setStartWorkDate(String.valueOf(result.getHiredDate()));
                // 工作地点
                dingUser.setWorkPlace(result.getWorkPlace());
                // 身份
                List<OapiV2UserGetResponse.UserRole> rolesList =
                    result.getRoleList() != null ? result.getRoleList() : null;
                if (rolesList != null) {
                    int count = rolesList.size();
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        list.add(rolesList.get(i).getName());
                    }
                    dingUser.setRoles(list.toString());
                } else {
                    dingUser.setRoles(null);
                }
                return dingUser;
            } else if (errCode == 60121) {
                // 找不到该用户 检查该企业下该员工是否存在
                return null;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
