package com.gdiot.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ding_process
 * @author 
 */
@Data
public class DingProcess implements Serializable {
    /**
     * 数据id
     */
    private Integer id;

    /**
     * 审批id
     */
    private String processId;

    /**
     * 审批标题
     */
    private String title;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String finishTime;

    /**
     * 发起人id
     */
    private String originatorUserId;

    /**
     * 发起人部门id
     */
    private String originatorDeptId;

    /**
     * 发起人部门
     */
    private String originatorDeptName;

    /**
     * 审批状态
     */
    private String status;

    /**
     * 审批结果
     */
    private String result;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 开票公司
     */
    private String invoiceCompany;

    /**
     * 合同名称及编号
     */
    private String contractNameAndNumber;

    /**
     * 合同
     */
    private String contract;

    /**
     * 开票事由
     */
    private String invoiceReason;

    /**
     * 发票种类
     */
    private String invoiceType;

    /**
     * 开票名称
     */
    private String invoiceName;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 地址和电话
     */
    private String addressAndTelephone;

    /**
     * 开户行及账号
     */
    private String bankAndAccount;

    /**
     * 开票内容
     */
    private String invoiceContent;

    /**
     * 开票金额
     */
    private String invoiceAmount;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 开票说明
     */
    private String invoiceExplain;

    /**
     * 要求开票时间
     */
    private String invoiceStartTime;

    /**
     * 预计回款时间
     */
    private String invoiceFinishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}