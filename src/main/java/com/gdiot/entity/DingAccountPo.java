package com.gdiot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Data
public class DingAccountPo {

    private int id;
    private String client;
    private String suiteId;
    private String appId;
    private String suiteKey;
    private String suiteSecret;

}
