package com.gdiot.action;

import com.gdiot.util.DingDataAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Controller
@RequestMapping("/dd")
public class DingDingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingController.class);

    /**
     * 查询钉钉部门用户列表
     *
     * @return
     */
    @RequestMapping("/getDeptUserList")
    @ResponseBody
    public String getDepUserList(@RequestBody Map<String, String> params) {
        String deptId = null;
        if (params != null) {

            if (params.containsKey("deptId")) {
                deptId = params.get("deptId");
            }
        }
        DingDataAnalysis mDingDataAnalysis = new DingDataAnalysis();
        String accessToken = mDingDataAnalysis.getToken();
        System.out.println("AccessToken=" + accessToken);
        List<String> deptUserList = mDingDataAnalysis.getDeptUserList(deptId, accessToken);
        LOGGER.info("usercount=" + deptUserList);
        return "dingding depUserList:" + deptUserList;
    }
}
