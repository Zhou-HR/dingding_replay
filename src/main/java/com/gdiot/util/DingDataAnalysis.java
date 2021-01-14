package com.gdiot.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
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
import com.gdiot.service.DingProcessService;
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
    private DingProcessService dingProcessService;

    /**
     * 获取Token
     *
     * @return
     */
    public String getToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingUtils.TOKEN_URL);
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey(DingUtils.APPKEY);
            req.setAppsecret(DingUtils.APPSECRET);
            req.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(req);
            return response.getAccessToken();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有部门下的用户详情，并保存数据库
     *
     * @param deptId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDeptUserDetail(String deptId, String accessToken) {
        System.out.println("deptId:" + deptId + "\n");
        //获取根目录下所有的子部门列表

        List<Long> subDeptList = getSubDeptList(deptId, accessToken);
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
     * 获取所有部门的详细信息
     *
     * @param deptId
     * @param accessToken
     * @return
     */
    public JSONArray getAllDeptDetail(String deptId, String accessToken) {
        System.out.println("deptId:" + deptId + "\n");
        if (dingDeptService == null) {
            dingDeptService = SpringContextUtils.getBean(DingDeptService.class);
        }
        // 获取根目录下所有的子部门列表
        List<Long> subDeptList = getSubDeptList(deptId, accessToken);
        JSONArray jsonarr = new JSONArray();
        if (subDeptList != null && subDeptList.size() > 0) {
            // 有子部门
            for (int i = 0; i < subDeptList.size(); i++) {
                String deptId0 = String.valueOf(subDeptList.get(i));
                System.out.println("depId0: " + i + ":" + deptId0 + "\n");

                // 获取部门详情
                DingDept dingDept = getDeptDetail(deptId0, accessToken);
                dingDeptService.insetDingDept(dingDept);

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
            // 获取部门详情
            DingDept dingDept = getDeptDetail(deptId, accessToken);
            dingDeptService.insetDingDept(dingDept);
            return null;
        }
        return jsonarr;
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
                dingUser.setPosition(result.getTitle());
                dingUser.setTelephone(result.getTelephone());
                dingUser.setMobile(result.getMobile());
                dingUser.setEmail(result.getEmail());
                //所在部门
                List<Long> deptIdList = result.getDeptIdList();
                switch (deptIdList.size()) {
                    case 1:
                        dingUser.setDept1(String.valueOf(deptIdList.get(0)));
                        break;
                    case 2:
                        dingUser.setDept1(String.valueOf(deptIdList.get(0)));
                        dingUser.setDept2(String.valueOf(deptIdList.get(1)));
                        break;
                    default:
                        break;
                }

                dingUser.setWorkNo(result.getJobNumber());
                // 入职时间
                dingUser.setStartWorkDate(String.valueOf(result.getHiredDate()));
                dingUser.setWorkPlace(result.getWorkPlace());
                // 身份
                List<OapiV2UserGetResponse.UserRole> rolesList = result.getRoleList() != null ? result.getRoleList() : null;
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
        } catch (
                ApiException e) {
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
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/get");
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(Long.valueOf(depId));
            OapiV2DepartmentGetResponse rsp = client.execute(req, accessToken);

            System.out.println("getErrcode=" + rsp.getErrcode());

            long errCode = rsp.getErrcode();
            OapiV2DepartmentGetResponse.DeptGetResponse result = rsp.getResult();
            System.out.println("result---" + result.toString());

            if (errCode == 0) {
                DingDept dingDept = new DingDept();
                dingDept.setDeptId(depId);
                dingDept.setDeptName(result.getName());
                dingDept.setParentId(String.valueOf(result.getParentId()));
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

    /**
     * 获取子部门列表
     *
     * @param deptId
     * @param accessToken
     * @return
     */
    public List<Long> getSubDeptList(String deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsubid");
            OapiV2DepartmentListsubidRequest req = new OapiV2DepartmentListsubidRequest();

            req.setDeptId(Long.valueOf(deptId));
            // 1 根目录

            OapiV2DepartmentListsubidResponse rsp = client.execute(req, accessToken);

            OapiV2DepartmentListsubidResponse.DeptListSubIdResponse result = rsp.getResult();

            List<Long> list = result.getDeptIdList();
            //System.out.println("list---" + list);
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取部门下的所有用户详情
     *
     * @param deptId
     */
    public void getDeptUserDetailSave(String deptId, String accessToken) {
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
     * @return
     */
    public List<String> getDeptUserList(String deptId, String accessToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listid");
            OapiUserListidRequest req = new OapiUserListidRequest();
            req.setDeptId(Long.valueOf(deptId));
            OapiUserListidResponse rsp = client.execute(req, accessToken);

            OapiUserListidResponse.ListUserByDeptResponse result = rsp.getResult();

            List<String> list = result.getUseridList();
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取所有开票审批的列表
     *
     * @param accessToken
     * @param params
     */
    public void getAllProcessDetail(String accessToken, Map<String, Object> params) {
        if (dingUserService == null) {
            dingUserService = SpringContextUtils.getBean(DingUserService.class);
        }
        if (dingProcessService == null) {
            dingProcessService = SpringContextUtils.getBean(DingProcessService.class);
        }

        long endTime = System.currentTimeMillis();
        //开票申请开始时间
        long startTime = endTime - 24 * 60 * 60 * 1000;
        if (null != params) {
            if (params.containsKey("startTime")) {
                startTime = (long) params.get("startTime");
            }
            if (params.containsKey("endTime")) {
                endTime = (long) params.get("endTime");
            }
        }
        log.info("startTime-----" + DateUtil.milliSecond2Date(String.valueOf(startTime), "yyyy-MM-dd HH:mm:ss"));
        log.info("endTime-----" + DateUtil.milliSecond2Date(String.valueOf(endTime), "yyyy-MM-dd HH:mm:ss"));

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
            OapiProcessinstanceListidsResponse rsp = client.execute(req, accessToken);
            if (rsp != null) {
                long errCode = rsp.getErrcode();
                if (errCode == 0) {
                    log.info("getBody-----" + rsp.getBody() + "\n");
                    PageResult result = rsp.getResult();
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
            OapiProcessinstanceGetRequest req = new OapiProcessinstanceGetRequest();
            req.setProcessInstanceId(processId);
            OapiProcessinstanceGetResponse rsp = client.execute(req, accessToken);
            long errCode = rsp.getErrcode();
            log.info("errCode-----" + errCode);

            DingProcess dingProcess;
            if (errCode == 0) {
                dingProcess = AnalysisProcessInstance(processId, rsp);
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
        if (vo.getFinishTime() != null) {
            dingProcess.setEndTime(DateUtil.milliSecond2Date(String.valueOf(vo.getFinishTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
        }
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


