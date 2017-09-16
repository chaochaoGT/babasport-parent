package com.ssm.core.service;

import com.ssm.core.dao.UserDao;
import com.ssm.core.pojo.Buyer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录的实现类
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    //查询单个用户
    @Override
    public Buyer findBuyerByUserName(String userName) {
        Buyer buyer=new Buyer();
        buyer.setUsername(userName);
        return userDao.selectOne(buyer);
    }
}
