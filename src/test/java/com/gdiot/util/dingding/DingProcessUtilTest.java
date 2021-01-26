package com.gdiot.util.dingding;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DingProcessUtilTest {

    @Test
    void getAllProcessDetail() {
        Map<String, Object> params = null;
        String accessToken = DingDingUtil.getToken();
        DingProcessUtil.getAllProcessDetail(accessToken, params);
    }

    @Test
    void getProcessInstance() {
        String accessToken = DingDingUtil.getToken();
        DingProcessUtil.getProcessInstance("1", accessToken);
    }
}