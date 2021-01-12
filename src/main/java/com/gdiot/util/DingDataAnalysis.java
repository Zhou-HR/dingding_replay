package com.gdiot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse.ProcessInstanceTopVo;
import com.dingtalk.api.response.OapiProcessinstanceListidsResponse.PageResult;
import com.gdiot.entity.DingDept;
import com.gdiot.entity.DingProcess;
import com.gdiot.entity.DingUser;
import com.gdiot.service.DingDeptService;
import com.gdiot.service.DingUserService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:对接钉钉的工具类
 * @Author:zhangjieqiong
 * @Date:2018/3/19 下午2:59
 */
@Slf4j
public class DingDataAnalysis {
    private DingUserService dingUserService;
    private DingDeptService dingDeptService;

    /**
     * 获取Token
     *
     * @return
     */
    public String getToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingUtils.tokenURL);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(DingUtils.APPKEY);
            request.setAppsecret(DingUtils.APPSECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有部门下的用户详情，并保存数据库
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDeptUserDetail(String depId, String accessToken) {
        System.out.println("depId:" + depId + "\n");
        //获取根目录下所有的子部门列表
        String subDepList = getSubDeptList(depId, accessToken);
        System.out.println("subDepList:" + subDepList + "\n");
        JSONArray jsonarr = new JSONArray();

        List<String> list = analysisDeptList(subDepList);
        if (list != null && list.size() > 0) {
            //有子部门
            for (int i = 0; i < list.size(); i++) {
                String depId0 = list.get(i);

                //获取部门下的用户
                getDeptUserDetailSave(depId0, accessToken);

                //获取子部门
                JSONArray subDeptList0 = getAllDeptUserDetail(depId0, accessToken);
                if (subDeptList0 != null && subDeptList0.size() > 0) {
                    jsonarr.add(subDeptList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            //无子部门，获取部门下用户
            getDeptUserDetailSave(depId, accessToken);
            return null;
        }
        return jsonarr;
    }

    /**
     * 获取所有部门的详细信息
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDeptDetail(String depId, String accessToken) {
        System.out.println("depId:" + depId + "\n");
        if (dingDeptService == null) {
            dingDeptService = SpringContextUtils.getBean(DingDeptService.class);
        }
        //获取根目录下所有的子部门列表
        String subDepList = getSubDeptList(depId, accessToken);
        JSONArray jsonarr = new JSONArray();

        List<String> list = analysisDeptList(subDepList);
        if (list != null && list.size() > 0) {
            //有子部门
            for (int i = 0; i < list.size(); i++) {
                String depId0 = list.get(i);
                System.out.println("depId0: " + i + ":" + depId0 + "\n");

                //获取部门详情
                DingDept dingDept = getDeptDetail(depId0, accessToken);
                dingDeptService.insetDingDept(dingDept);

                //获取下一层子部门
                JSONArray subDeptList0 = getAllDeptDetail(depId0, accessToken);
                if (subDeptList0 != null && subDeptList0.size() > 0) {
                    System.out.println("subDepList0: " + subDeptList0 + "\n");
                    jsonarr.add(subDeptList0.toString());
                } else {
                    continue;
                }
            }
        } else {
            //无子部门，获取部门详情
            //获取部门详情
            DingDept dingDept = getDeptDetail(depId, accessToken);
            dingDeptService.insetDingDept(dingDept);
            return null;
        }
        return jsonarr;
    }

    /**
     * 获取所有用户的父部门
     *
     * @param accessToken
     */
    public void getAllUserParentDept(String accessToken) {
        if (dingUserService == null) {
            dingUserService = SpringContextUtils.getBean(DingUserService.class);
        }
        List<DingUser> list = dingUserService.selectAllUserId();
        for (DingUser user : list) {
            String userId = user.getUserId();

            DingUser dingUser = getUserParentDeptId(userId, accessToken);
            if (dingUser != null) {
                dingUserService.updateUserDept(dingUser);
            } else {
                continue;
            }
        }
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @param accessToken
     * @return
     */
    public DingUser getUserDetail(String userId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);

            System.out.print("getErrcode=" + response.getErrcode() + "\n");

            long errCode = response.getErrcode();
            if (errCode == 0) {
                DingUser dingUser = new DingUser();
                dingUser.setUserId(response.getUserid());
                dingUser.setName(response.getName());
                dingUser.setUnionid(response.getUnionid());
                dingUser.setManagerId(response.getManagerUserId());
                dingUser.setPosition(response.getPosition());
                dingUser.setTel(response.getTel());
                dingUser.setMobile(response.getMobile());
                dingUser.setEmail(response.getEmail());

                dingUser.setDept1(String.valueOf(response.getDepartment()));
                dingUser.setWorkNo(response.getJobnumber());

                //入职时间
                dingUser.setStartWorkDate(String.valueOf(response.getHiredDate()));
                dingUser.setWorkPlace(response.getWorkPlace());

                //身份
                List<OapiUserGetResponse.Roles> rolesList = response.getRoles() != null ? response.getRoles() : null;
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
                //找不到该用户 检查该企业下该员工是否存在
                return null;
            } else {
                return null;
            }

        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门详情
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public DingDept getDeptDetail(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
            OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
            request.setId(depId);
            request.setHttpMethod("GET");
            OapiDepartmentGetResponse response = client.execute(request, accessToken);

            System.out.print("getErrcode=" + response.getErrcode() + "/n");

            DingDept dingDept = new DingDept();
            dingDept.setDeptId(depId);
            dingDept.setDeptName(response.getName());
            dingDept.setParentId(String.valueOf(response.getParentid()));
            dingDept.setSourceidentifier(response.getSourceIdentifier());
            dingDept.setDeptManagerId(response.getDeptManagerUseridList());
            return dingDept;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取子部门列表
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getSubDeptList(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
            OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();

            request.setId(depId);
            //1根目录

            request.setHttpMethod("GET");
            OapiDepartmentListIdsResponse response = client.execute(request, accessToken);
            String list = response.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门下的所有用户详情
     *
     * @param depId
     */
    public void getDeptUserDetailSave(String depId, String accessToken) {
        if (dingUserService == null) {
            dingUserService = SpringContextUtils.getBean(DingUserService.class);
        }
        //获取指定部门ID的所有用户userID列表
        String deptUserList = getDeptUserList(depId, accessToken);
        System.out.println("depUserList: " + deptUserList + "\n");

        List<String> list = analysisUserList(deptUserList);
        if (list != null && list.size() > 0) {//部门下有用户
            for (int i = 0; i < list.size(); i++) {
                String userID0 = list.get(i);

                //获取用户详情
                DingUser dingUser = getUserDetail(userID0, accessToken);

                if (dingUser != null) {
                    //获取用户的所有父部门列表
                    DingUser dingUserDept = getUserParentDeptId(userID0, accessToken);
                    dingUser.setDept1(dingUserDept.getDept1());
                    dingUser.setDept2(dingUserDept.getDept2());
                    dingUser.setDept3(dingUserDept.getDept3());
                    dingUser.setDept4(dingUserDept.getDept4());
                    dingUser.setDept5(dingUserDept.getDept5());
                    dingUserService.insertDingUser(dingUser);
                } else {
                    continue;
                }
            }
        } else {//部门下无用户

        }
    }

    /**
     * 获取部门用户列表
     *
     * @param depId
     * @param accessToken
     * @return
     */
    public String getDeptUserList(String depId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
            OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
            req.setDeptId(depId);
            req.setHttpMethod("GET");
            OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
            String list = rsp.getBody();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定用户的所有父部门ID
     *
     * @param userId
     * @param accessToken
     * @return
     */
    public DingUser getUserParentDeptId(String userId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts");
            OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
            request.setUserId(userId);
            request.setHttpMethod("GET");
            OapiDepartmentListParentDeptsResponse response = client.execute(request, accessToken);
            System.out.print("getCode=" + response.getCode() + "\n");
            System.out.print("getErrorCode=" + response.getErrorCode() + "\n");
            long errCode = response.getErrcode();
            String dep = response.getDepartment();
            String body = response.getBody();

            if (errCode == 0) {
                DingUser dingUser = new DingUser();
                dingUser.setUserId(userId);

                System.out.println("depList=" + dep);
                List<List<Long>> Alllist = new ArrayList<List<Long>>();
                JSONArray arr = JSONArray.parseArray(dep);
                for (Object obj : arr) {
                    String json = obj.toString();
                    JSONArray arr0 = JSONArray.parseArray(json);
                    int count = arr0.size();
                    ArrayList<Long> list = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        list.add(arr0.getLong(i));
                        System.out.println("depl00=" + arr0.getLong(i));
                    }
                    if (count == 6) {
                        dingUser.setDept1(String.valueOf(arr0.getLong(4)));
                        dingUser.setDept2(String.valueOf(arr0.getLong(3)));
                        dingUser.setDept3(String.valueOf(arr0.getLong(2)));
                        dingUser.setDept4(String.valueOf(arr0.getLong(1)));
                        dingUser.setDept5(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 5) {
                        dingUser.setDept1(String.valueOf(arr0.getLong(3)));
                        dingUser.setDept2(String.valueOf(arr0.getLong(2)));
                        dingUser.setDept3(String.valueOf(arr0.getLong(1)));
                        dingUser.setDept4(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 4) {
                        dingUser.setDept1(String.valueOf(arr0.getLong(2)));
                        dingUser.setDept2(String.valueOf(arr0.getLong(1)));
                        dingUser.setDept3(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 3) {
                        dingUser.setDept1(String.valueOf(arr0.getLong(1)));
                        dingUser.setDept2(String.valueOf(arr0.getLong(0)));
                    }
                    if (count == 2) {
                        dingUser.setDept1(String.valueOf(arr0.getLong(0)));
                    }
                    Alllist.add(list);
                }
                return dingUser;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析部门列表，返回部门列表list
     *
     * @param subDepList
     * @return
     */
    public List<String> analysisDeptList(String subDepList) {
        List<String> list = new ArrayList<>();
        list.clear();
        //解析子部门列表
        JSONObject depjson = JSONObject.parseObject(subDepList);
        if (depjson != null && depjson.containsKey("sub_dept_id_list")) {
            JSONArray sub_dept_id_list = depjson.getJSONArray("sub_dept_id_list");
            int sub_dept_id_list_size = sub_dept_id_list.size();
            for (int i = 0; i < sub_dept_id_list_size; i++) {
                long depId0 = sub_dept_id_list.getLong(i);
                String depIdStr = String.valueOf(depId0);
                list.add(depIdStr);
            }
        }
        return list;
    }

    /**
     * 解析用户列表，返回用户列表list
     *
     * @param depUserList
     * @return
     */
    public List<String> analysisUserList(String depUserList) {
        List<String> list = new ArrayList<>();
        list.clear();
        JSONObject json = JSONObject.parseObject(depUserList);

        if (json != null && json.containsKey("userIds")) {
            JSONArray userIds = json.getJSONArray("userIds");
            System.out.println("userIds=" + userIds.toString() + "\n");
            int userIdCount = userIds.size();
            System.out.println("userIdCount=" + userIdCount + "\n");
            for (int i = 0; i < userIdCount; i++) {
                String userId0 = userIds.getString(i);
                System.out.println("userId0: " + userId0 + "\n");
                list.add(userId0);
            }
        }
        return list;
    }

    /**
     * 根据用户ID，获取指定时间段开票审批流
     *
     * @param startTime
     * @param endTime
     * @param userId
     * @param accessToken
     * @return
     */
    public List<String> getProcessListId(long startTime, long endTime, String userId, String accessToken) {
        try {
            List<String> id_list = new ArrayList<>();
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/listids");
            OapiProcessinstanceListidsRequest req = new OapiProcessinstanceListidsRequest();
            req.setProcessCode(DingUtils.PROCESS_CODE);
            req.setStartTime(startTime);
            req.setEndTime(endTime);
            req.setSize(10L);
            req.setCursor(0L);
            req.setUseridList(userId);
            OapiProcessinstanceListidsResponse response = client.execute(req, accessToken);
            if (response != null) {
                long errCode = response.getErrcode();
                if (errCode == 0) {
                    log.info("getBody-----" + response.getBody() + "\n");
                    PageResult result = response.getResult();
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
     * @return
     */
    public DingProcess getProcessInstance(String processId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processId);
            OapiProcessinstanceGetResponse response = client.execute(request, accessToken);
            long errCode = response.getErrcode();
            log.info("errCode-----" + errCode);

            DingProcess dingProcess = new DingProcess();
            if (errCode == 0) {
                dingProcess = AnalysisProcessInstance(processId, response);
                return dingProcess;
            } else {
                return null;
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有改造审批单的列表
     *
     * @param accessToken
     * @param params
     */
   /* public void getAllProcessList(String accessToken, Map<String, Object> params) {
        if (mIDingDingUserService == null) {
            mIDingDingUserService = SpringContextUtils.getBean(IDingDingUserService.class);
        }
        if (mIDingAssessService == null) {
            mIDingAssessService = SpringContextUtils.getBean(IDingAssessService.class);
        }

        long endTime = System.currentTimeMillis();
        long startTime = endTime - 24 * 60 * 60 * 1000;
        if (params != null) {
            if (params.containsKey("startTime")) {
                startTime = (long) params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTime = (long) params.get("endTime");
            }
        }
        log.info("startTime-----" + DateUtil.milliSecond2Date(String.valueOf(startTime), "yyyy-MM-dd HH:mm:ss"));
        log.info("endTime-----" + DateUtil.milliSecond2Date(String.valueOf(endTime), "yyyy-MM-dd HH:mm:ss"));

//			JSONArray jsonArr = new JSONArray();
        List<DingDingUser> list = mIDingDingUserService.selectAllUserId();
        for (DingDingUser user : list) {
            String userId = null;
            try {
                userId = user.getDdId();
            } catch (Exception e) {
                log.info("e=" + e.toString());
            }
            if (userId != null) {
                log.info("userId-----" + userId);
                List<String> id_list = getAssessListId(startTime, endTime, userId, accessToken);
                log.info("id_list=" + id_list.toString());
                if (id_list != null && id_list.size() > 0) {
                    String dd_id;
                    String assess_list;
                    String start_time;
                    String end_time;
                    String create_time;
                    DingAssessPo mDingAssessPo = new DingAssessPo();
                    mDingAssessPo.setDd_id(userId);
                    mDingAssessPo.setAssess_list(id_list.toString());
                    mDingAssessPo.setStart_time(String.valueOf(startTime));
                    mDingAssessPo.setEnd_time(String.valueOf(endTime));
                    mIDingAssessService.insertDingAssess(mDingAssessPo);

                    int list_size = id_list.size();
                    for (int i = 0; i < list_size; i++) {
                        String assessId = id_list.get(i);
                        DingAssessDetailPo mDingAssessDetailPo = getAssessInstance(assessId, accessToken);
                        if (mDingAssessDetailPo != null) {
                            mIDingAssessService.insertDingAssessDetail(mDingAssessDetailPo);
                        }
                        String proj_code = mDingAssessDetailPo.getProjectCode();
                        String applyId = mDingAssessDetailPo.getBusinessCode();
                        String applyReasonType = mDingAssessDetailPo.getApplyReasonType();
                        String auditResult = mDingAssessDetailPo.getAuditResult();
                        String auditStatus = mDingAssessDetailPo.getAuditStatus();
                        if ("agree".equals(auditResult) && "COMPLETED".equals(auditStatus)) {//审批通过的
                            int result = JdbcErpOPRView.selectORPByProjCode(proj_code, applyId, applyReasonType, auditResult);
                            int result2 = JdbcErpZYView.selectZYByProjCode(proj_code, applyId, applyReasonType, auditResult);
                        }
                    }
                } else {
                    continue;
                }

            } else {
                continue;
            }

        }
//			return jsonArr;
    }*/

    /**
     * 解析实例详情
     *
     * @param processId
     * @param response
     * @return
     */
    public DingProcess AnalysisProcessInstance(String processId, OapiProcessinstanceGetResponse response) {

        ProcessInstanceTopVo vo = response.getProcessInstance();
        //实例详情
        log.info("getBody-----" + response.getBody() + "\n");

        log.info("getTitle审批实例标题-----" + vo.getTitle());
        //审批实例标题
        log.info("getCreateTime开始时间-----" + vo.getCreateTime());
        //开始时间
        log.info("getFinishTime结束时间-----" + vo.getFinishTime());
        //结束时间
        log.info("getOriginatorUserid发起人-----" + vo.getOriginatorUserid());
        //发起人
        log.info("getOriginatorDeptId发起部门-----" + vo.getOriginatorDeptId());
        //发起部门
        log.info("getStatus审批状态-----" + vo.getStatus());
        //审批状态，分为NEW（新创建）RUNNING（运行中）TERMINATED（被终止）COMPLETED（完成）
        log.info("getCcUserids抄送人-----" + vo.getCcUserids());
        //抄送人
        log.info("getResult结果-----" + vo.getResult());
        //结果，分为NONE（无），AGREE（同意），REFUSE（拒绝），REDIRECTED（转交）
        log.info("getBusinessId审批实例业务编号-----" + vo.getBusinessId());
        //审批实例业务编号
        log.info("getOriginatorDeptName发起部门-----" + vo.getOriginatorDeptName());
        //发起部门
        List<OapiProcessinstanceGetResponse.FormComponentValueVo> formList = vo.getFormComponentValues();
        DingProcess dingProcess = analysisProcessFormValues(formList);

        dingProcess.setProcessId(processId);
        dingProcess.setTitle(vo.getTitle());
        dingProcess.setStartTime(DateUtil.milliSecond2Date(String.valueOf(vo.getCreateTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
        dingProcess.setFinishTime(DateUtil.milliSecond2Date(String.valueOf(vo.getFinishTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
        dingProcess.setOriginatorUserId(vo.getOriginatorUserid());
        dingProcess.setOriginatorDeptId(vo.getOriginatorDeptId());
        dingProcess.setOriginatorDeptName(vo.getOriginatorDeptName());
        dingProcess.setStatus(vo.getStatus());
        dingProcess.setResult(vo.getResult());
        dingProcess.setBusinessId(vo.getBusinessId());
        return dingProcess;
    }

    /**
     * 解析开票数据表单
     *
     * @param list
     * @return
     */
    public DingProcess analysisProcessFormValues(List<OapiProcessinstanceGetResponse.FormComponentValueVo> list) {
        System.out.println("-------------------------------analysis Process FormValues----------------------------------- " + "\n");
        DingProcess dingProcess = new DingProcess();
        try {
            if (list != null && list.size() > 0) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : list) {
                    String name = vo.getName();
                    String value = vo.getValue();
                    String id = vo.getId();
                    log.info("name: " + name + "\n");
                    log.info("value: " + value + "\n");

                    if ("开票公司".equals(name) || "DDSelectField_1ITKOQRG3DQ80".equals(id)) {
                        dingProcess.setInvoiceCompany(value);
                    } else if ("合同名称及编号".equals(name) || "TextField-JZCI6WBK".equals(id)) {
                        dingProcess.setContractNameAndNumber(value);
                    } else if ("合同（附件）".equals(name) || "DDAttachment_13F6MHAAZCZG0".equals(id)) {
                        dingProcess.setContract(value);
                    } else if ("本次开票事由".equals(name) || "TextField_1R5WIZDM0LKW0".equals(id)) {
                        dingProcess.setInvoiceReason(value);
                    } else if ("发票种类".equals(name) || "DDSelectField_1CRQBOWMXAPS0".equals(id)) {
                        dingProcess.setInvoiceType(value);
                    } else if ("开票名称".equals(name) || "TextField-JZCI6WBQ".equals(id)) {
                        dingProcess.setInvoiceName(value);
                    } else if ("纳税人识别号".equals(name) || "TextField-JZCI6WBT".equals(id)) {
                        dingProcess.setTaxpayerNumber(value);
                    } else if ("地址和电话".equals(name) || "TextField-JZCI6WBV".equals(id)) {
                        dingProcess.setAddressAndTelephone(value);
                    } else if ("开户行及账号".equals(name) || "TextField-JZCI6WBX".equals(id)) {
                        dingProcess.setBankAndAccount(value);
                    } else if (("开票内容（品名）").equals(name) || "TextField_4EIAT41VDE60".equals(id)) {
                        dingProcess.setInvoiceContent(value);
                    } else if (("开票金额（元）（含税）").equals(name) || "MoneyField-JZCI6WC1".equals(id)) {
                        dingProcess.setInvoiceAmount(value);
                    } else if (("税率").equals(name) || "DDSelectField_23C12YMM130G0".equals(id)) {
                        dingProcess.setTaxRate(value);
                    } else if (("开票说明").equals(name) || "TextField_ZGKEYZ6U8740".equals(id)) {
                        dingProcess.setInvoiceExplain(value);
                    } else if (("要求开票时间").equals(name) || "DDDateField_51M80I7SAX00".equals(id)) {
                        dingProcess.setInvoiceStartTime(value);
                    } else if (("预计回款时间").equals(name) || "DDDateField_ZWOV03BUSW00".equals(id)) {
                        dingProcess.setInvoiceFinishTime(value);
                    }
                }
            }
        } catch (JSONException e) {
            log.info("JSONException e=" + e);
        }
        System.out.println("-------------------------------analysis Process FormValues  end----------------------------------- " + "\n");
        return dingProcess;
    }

}


