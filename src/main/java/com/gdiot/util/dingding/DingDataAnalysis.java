package com.gdiot.util.dingding;

import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse.ProcessInstanceTopVo;
import com.gdiot.entity.DingProcess;
import com.gdiot.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉数据解析工具类
 *
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@Slf4j
public class DingDataAnalysis {
    /**
     * 解析实例详情
     *
     * @param processId
     * @param response
     * @return com.gdiot.entity.DingProcess
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static DingProcess AnalysisProcessInstance(String processId, OapiProcessinstanceGetResponse response) {

        ProcessInstanceTopVo vo = response.getProcessInstance();
        // 实例详情
        log.info("getBody-----" + response.getBody() + "\n");

        log.info("getTitle审批实例标题-----" + vo.getTitle());
        // 审批实例标题
        log.info("getCreateTime开始时间-----" + vo.getCreateTime());
        // 开始时间
        log.info("getFinishTime结束时间-----" + vo.getFinishTime());
        // 结束时间
        log.info("getOriginatorUserid发起人-----" + vo.getOriginatorUserid());
        // 发起人
        log.info("getOriginatorDeptId发起部门-----" + vo.getOriginatorDeptId());
        // 发起部门
        log.info("getStatus审批状态-----" + vo.getStatus());
        // 审批状态，分为NEW（新创建）RUNNING（运行中）TERMINATED（被终止）COMPLETED（完成）
        log.info("getCcUserids抄送人-----" + vo.getCcUserids());
        // 抄送人
        log.info("getResult结果-----" + vo.getResult());
        // 结果，分为NONE（无），AGREE（同意），REFUSE（拒绝），REDIRECTED（转交）
        log.info("getBusinessId审批实例业务编号-----" + vo.getBusinessId());
        // 审批实例业务编号
        log.info("getOriginatorDeptName发起部门-----" + vo.getOriginatorDeptName());
        // 发起部门
        List<OapiProcessinstanceGetResponse.FormComponentValueVo> formList = vo.getFormComponentValues();
        DingProcess dingProcess = analysisProcessFormValues(formList);

        dingProcess.setProcessId(processId);
        dingProcess.setTitle(vo.getTitle());
        dingProcess.setStartTime(
            DateUtil.milliSecond2Date(String.valueOf(vo.getCreateTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
        if (vo.getFinishTime() != null) {
            dingProcess.setEndTime(
                DateUtil.milliSecond2Date(String.valueOf(vo.getFinishTime().getTime()), "yyyy-MM-dd HH:mm:ss"));
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
     * @return com.gdiot.entity.DingProcess
     * @author ZhouHR
     * @date 2021/01/20 19:47
     */
    public static DingProcess
        analysisProcessFormValues(List<OapiProcessinstanceGetResponse.FormComponentValueVo> list) {
        System.out.println(
            "-------------------------------analysis Process FormValues----------------------------------- " + "\n");
        DingProcess dingProcess = new DingProcess();
        try {
            if (list != null && list.size() > 0) {
                for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : list) {
                    String name = vo.getName();
                    String value = vo.getValue();
                    String id = vo.getId();
                    log.info("name: " + name + "\n");
                    log.info("value: " + value + "\n");
                    // 解析
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
        System.out.println(
            "-------------------------------analysis Process FormValues  end----------------------------------- "
                + "\n");
        return dingProcess;
    }

}
