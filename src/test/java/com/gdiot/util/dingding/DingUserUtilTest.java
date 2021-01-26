package com.gdiot.util.dingding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DingUserUtilTest {

    @Test
    void getAllDeptUserDetail() {
        String accessToken = DingDingUtil.getToken();
        DingUserUtil.getAllDeptUserDetail("1", accessToken);
    }
}