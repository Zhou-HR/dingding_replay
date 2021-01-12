package com.gdiot.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ding_user
 *
 * @author ZhouHR
 * @date 2021/01/12
 */
@Data
public class DingUser implements Serializable {
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
     * 唯一标识
     */
    private String unionid;

    /**
     * 主管的用户id
     */
    private String managerId;

    /**
     * 职位
     */
    private String position;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份
     */
    private String roles;

    /**
     * 部门id
     */
    private String dept1;

    /**
     * 父部门
     */
    private String dept2;

    /**
     * 父部门
     */
    private String dept3;

    /**
     * 父部门
     */
    private String dept4;

    /**
     * 父部门
     */
    private String dept5;

    /**
     * 员工工号
     */
    private String workNo;

    /**
     * 入职时间
     */
    private String startWorkDate;

    /**
     * 办公地点
     */
    private String workPlace;

    /**
     * 工作时间
     */
    private String workAge;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 离职状态 1 为 离职
     */
    private Byte dimission;

    private static final long serialVersionUID = 1L;
}