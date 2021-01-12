package com.gdiot.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String appKey;

    private String appSecret;

}
