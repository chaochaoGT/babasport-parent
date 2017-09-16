package com.ssm.core.service;

import com.ssm.core.Session.SessionTool;
import com.ssm.core.pojo.Buyer;
import org.springframework.web.context.ServletContextAware;

/**
 * 用户登录Session的接口
 *
 */
public interface SessionService {


    /**
     * 用户登录成功后,添加loginUSerid到redis中
     * @param loginUserID    想浏览器中存入cookie后返回LoginUserID,
     * @param userName
     */
    public void addLoginUserToRedis(String loginUserID,String userName);

    /**
     * 根据cookie中的loginUserID获取用户名
     * @param key
     * @return
     */
    public String getLoginUserFromRedis(String key);
}
