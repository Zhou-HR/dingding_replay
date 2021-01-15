package com.gdiot.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * finance_notify
 *
 * @author ZhouHR
 * @date 2021/01/14
 */
@Data
public class FinanceNotify implements Serializable {
    /**
     * 数据id
     */
    private Integer id;

    /**
     * 钉钉用户id
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 是否需要通知，1需要，0不需要
     */
    private Integer notify;

    private static final long serialVersionUID = 1L;
}