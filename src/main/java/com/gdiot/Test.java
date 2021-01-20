package com.gdiot;

import com.gdiot.util.DateUtil;

import java.math.BigDecimal;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
public class Test {
    public static void main(String[] args) {
        long endTime = System.currentTimeMillis();

        BigDecimal a = new BigDecimal(endTime);
        System.out.println(a);
        BigDecimal b = new BigDecimal(24 * 60 * 60 * 1000).multiply(BigDecimal.valueOf(365));

        // 开票申请开始时间
        long startTime = a.subtract(b).longValue();
        System.out.println(DateUtil.milliSecond2Date(String.valueOf(startTime), "yyyy-MM-dd HH:mm:ss"));
    }
}
