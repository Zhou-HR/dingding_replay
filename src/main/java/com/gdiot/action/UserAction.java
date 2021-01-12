package com.gdiot.action;

import com.gdiot.dao.UserMapper;
import com.gdiot.entity.User;
import com.gdiot.session.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Controller
//@RequestMapping("/user")
public class UserAction {

    @Autowired
    UserMapper userMapper;

    @Autowired
    SessionUtil sessionUtil;

    @RequestMapping("/gettoken")
    @ResponseBody
    public Map<String, String> gettoken(String appKey, String appSecret) {
        Map<String, String> map = new HashMap<>();
        map.put("code", "-1");
        map.put("msg", "参数错误");

        if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(appSecret)) {
            return map;
        }

        List<User> list = userMapper.getUser(appKey, appSecret);

        if (list.size() != 1) {
            return map;
        }

        User user = list.get(0);
//		String enc=System.currentTimeMillis()+user.getRealname()+user.getName()+user.getDept();
//		String base64=new sun.misc.BASE64Encoder().encode(enc.getBytes());

        //放入redis session,把原来的失效
        String token = sessionUtil.setSession(user);

        map.put("token", token);
        map.put("code", "0");
        map.put("msg", "success");

//		sessionUtil.setSession(base64, user);
        return map;
    }

}
