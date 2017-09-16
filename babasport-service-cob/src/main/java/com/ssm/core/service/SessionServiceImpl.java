package com.ssm.core.service;

import com.ssm.core.dao.UserDao;
import com.ssm.core.pojo.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;

/**
 * 用户登录的实现类
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

    @Autowired
    private Jedis jedis;


    @Override
    public void addLoginUserToRedis(String loginUserID, String userName) {

        //key用作验证码      ????????
        jedis.set(loginUserID+":username",userName);

        //设置存活时间
        jedis.expire(loginUserID+":username",5000);
    }

    @Override
    public String getLoginUserFromRedis(String key) {
        String username = jedis.get(key + ":username");
        //设置存活时间
        jedis.expire(key+":username",5000);
        return username;
    }


}
