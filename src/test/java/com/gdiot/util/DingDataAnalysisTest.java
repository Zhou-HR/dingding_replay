package com.gdiot.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/20 19:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DingDataAnalysisTest {

    @Test
    void getAllDeptUserDetail() {
        DingDataAnalysis dataAnalysis = new DingDataAnalysis();
        String accessToken = dataAnalysis.getToken();
        dataAnalysis.getAllDeptUserDetail("1", accessToken);
    }

    @Test
    void getAllDeptDetail() {
        DingDataAnalysis dataAnalysis = new DingDataAnalysis();
        String accessToken = dataAnalysis.getToken();
        dataAnalysis.getAllDeptDetail("1", accessToken);
    }

    @Test
    void getProcessInstance() {
        DingDataAnalysis dataAnalysis = new DingDataAnalysis();
        String accessToken = dataAnalysis.getToken();
        dataAnalysis.getProcessInstance("1", accessToken);
    }

    @Test
    void getAllProcessDetail() {
        Map<String, Object> params = null;
        DingDataAnalysis dataAnalysis = new DingDataAnalysis();
        String accessToken = dataAnalysis.getToken();
        dataAnalysis.getAllProcessDetail(accessToken, params);
    }
}