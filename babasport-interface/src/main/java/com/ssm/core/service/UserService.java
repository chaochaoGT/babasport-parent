package com.ssm.core.service;

import com.ssm.core.pojo.Buyer;

/**
 * 用户登录的接口
 *
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
  public Buyer findBuyerByUserName(String userName);
}
