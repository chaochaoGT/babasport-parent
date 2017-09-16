package com.ssm.core.service;

import com.ssm.core.dao.TestDao;
import com.ssm.core.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/8/13.
 */
@Service("testMyService")
@Transactional
public class TestMyServiceImpl implements TestMyService {

    @Autowired
    private TestDao testDao;

    @Override
    public void add(TbUser user) {
        testDao.add(user);
    }
}
