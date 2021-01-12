package com.gdiot.action;

import com.gdiot.dao.DingDingUserMapper;
import com.gdiot.entity.DingDingUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestAction {

    @Autowired
    private DingDingUserMapper dingDingUserMapper;

    @RequestMapping("/selectTbData")
    @ResponseBody
    public List<DingDingUser> selectTbData() {

        DingDingUser dingDingUser = new DingDingUser();

        dingDingUser.setDdId("virus");

        dingDingUserMapper.insertDingDingUser(dingDingUser);
        dingDingUserMapper.updateidDingDingUser(dingDingUser);
        return null;

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        log.info("test===========@@@@@@@@@@@@@");
        log.info("测试页面");
        System.out.println("测试页面");
    }

}
