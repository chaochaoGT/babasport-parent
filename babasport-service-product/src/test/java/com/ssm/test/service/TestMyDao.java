package com.ssm.test.service;

import com.ssm.core.service.TestMyService;
import com.ssm.core.dao.TestDao;
import com.ssm.core.pojo.TbUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/8/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:applicationContext.xml"})
public class TestMyDao {

    @Autowired
    private TestDao testDao;

    @Autowired
    private TestMyService testMyService;

    @Test
    public void testAdd() {
        TbUser user = new TbUser();
        user.setName("襄阳");
        user.setSex("女");
       testDao.add(user);
    }
    @Test
    public void testAdd2() {
        TbUser user = new TbUser();
        user.setName("襄阳1");
        user.setSex("女");
       testMyService.add(user);
    }


}
