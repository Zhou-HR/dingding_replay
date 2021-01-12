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
public class DingDepPo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String dep_id;
    private String dep_detail;
    private String dep_name;
    private String parent_id;
    //	String AutoAddUser;
    private String Code;
    //	String CreateDeptGroup;
    private String DeptManagerUseridList;
    private String DeptPerimits;
    private String DeptPermits;
    //	String GroupContainSubDept;
//	String OuterDept;
    private String UserPerimits;
    private String UserPermits;

}
