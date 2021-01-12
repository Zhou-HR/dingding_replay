package com.gdiot.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Data
public class DingUserInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String roles;
    private String userid;
    private String isLeaderInDepts;
    private boolean isBoss;
    /**
     * 聘用日期
     */
    private long hiredDate;
    private boolean isSenior;
    private String department;
    private String orderInDepts;
    private boolean active;
    private String avatar;
    private boolean isAdmin;
    private String tags;
    private boolean isHide;
    private String jobnumber;
    private String name;
    private String position;

//	private String work_no;
//	private String erp_no;
//	private String email;
//	private String mobile;
//	private String oa_no;

}
