package com.gdiot.util;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Slf4j
public class DingDingUtil {

    private static final String DINGDING_URL = PropertiesUtil.getValue("dingding_url");

    public static String send(String userCode, String userID, String msg) {
        msg = DateUtil2.getTodayTime() + " " + msg;
        Map<String, String> param = new HashMap<>();
        //toDo 正式要被注释
        userID = "350002";
        log.info("===============20190709yxl  钉钉发消息 ==================" + userCode + " " + userID + " " + msg);
        if (StringUtils.isEmpty(userID)) {
            return "钉钉userId为空";
        }
        param.put("userID", userID);
        param.put("msg", msg);
        String result = "超时";
        try {
            result = HttpClientUtil.postJson(DINGDING_URL, param, "UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return result;

    }

}
