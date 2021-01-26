package com.gdiot.util.dingding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DingDeptUtilTest {

    @Test
    void getAllDeptDetail() {
        String accessToken = DingDingUtil.getToken();
        DingDeptUtil.getAllDeptDetail("1", accessToken);
    }
}