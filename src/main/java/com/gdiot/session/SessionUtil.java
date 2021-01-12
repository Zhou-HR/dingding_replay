package com.gdiot.session;

import com.gdiot.entity.User;
import com.gdiot.redis.JedisUtil;
import com.gdiot.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhouHR
 * @date 2021/01/12
 */
@Component
public class SessionUtil {

    @Autowired
    JedisUtil jedisUtil;

    public static final int sessionTimeOut = 7800;//秒 2小时

    private static final String redisProjectUniqueName = "sp_ding_manager";

    private String generateToken(User user) {
        String enc = redisProjectUniqueName + System.currentTimeMillis() + user.getAppKey() + user.getName() + user.getAppSecret();
//		String base64=new sun.misc.BASE64Encoder().encode(enc.getBytes());
        String token = MD5Util.getPwd(enc);

        String key = redisProjectUniqueName + "userName" + user.getId();
        String keyStartTime = redisProjectUniqueName + "keyStartTime" + user.getId();

        jedisUtil.set(keyStartTime + token, System.currentTimeMillis(), sessionTimeOut);
        jedisUtil.set(token, user, sessionTimeOut);
        jedisUtil.set(key, token, sessionTimeOut);

        return token;
    }

    public User getSessionUser(HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = (User) jedisUtil.get(token);

        return user;
    }

    public User getSessionUser(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        User user = (User) jedisUtil.get(token);
        //更新失效时间
        if (user != null) {
            setSession(user);
        }

        return user;
    }

    //保证只有一个token有效
    public String setSession(User user) {
        //用key来保证token唯一
        String key = redisProjectUniqueName + "userName" + user.getId();
        String keyStartTime = redisProjectUniqueName + "keyStartTime" + user.getId();
//		String key=user.getUserId()+user.getCompanyId();
        String token = (String) jedisUtil.get(key);
        Long t1 = (Long) jedisUtil.get(keyStartTime + token);
        //20190716yxl 保证多账号同时登录，不会互相踢下去，符合正常的浏览器规则？？？
        if (token != null && t1 != null) {
            //那就不删除啦，让token自然死亡吧
//			jedisUtil.delete(key);
//			jedisUtil.delete(oldToken);

            long t2 = System.currentTimeMillis();
            System.out.println((t2 - t1) / 1000L);
            if ((t2 - t1) / 1000L > 7200L) {//已经满2个小时了
                token = this.generateToken(user);//新生成的
            }

        } else {//新生成的

            token = this.generateToken(user);

        }


//		JedisUtil.set(token, user,20);
//		JedisUtil.set(key, token,20);

        return token;
    }


}
