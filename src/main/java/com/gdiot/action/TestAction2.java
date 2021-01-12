package com.gdiot.action;

import com.gdiot.dao.DingDingUserMapper;
import com.gdiot.entity.DingUserResult;
import com.gdiot.redis.JedisUtil;
import com.gdiot.session.RequestProcess;
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
@RequestMapping("/test2")
public class TestAction2 {

    @Autowired
    private DingDingUserMapper dingDingUserMapper;

    @Autowired
    JedisUtil jedisUtil;

    @RequestMapping("/wait")
    @ResponseBody
    public List<DingUserResult> wait1() {

        try {
            Thread.sleep(900000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dingDingUserMapper.selectDingDingUser(1, 10);

    }

    @RequestMapping("/selectTbData")
    @ResponseBody
    @RequestProcess
    public List<DingUserResult> selectTbData() {

        jedisUtil.set("test", "test");

        return dingDingUserMapper.selectDingDingUser(1, 10);

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public void test() {
        log.info("test===========@@@@@@@@@@@@@");
        log.info("测试页面");
        System.out.println("测试页面");
    }
}
