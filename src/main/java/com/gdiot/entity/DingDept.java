package com.gdiot.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ding_dept
 *
 * @author
 */
@Data
public class DingDept implements Serializable {
    /**
     * 数据id
     */
    private Integer id;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 父部门id
     */
    private String parentId;

    /**
     * 部门标识
     */
    private String sourceidentifier;

    /**
     * 部门主管id
     */
    private String deptManagerId;

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