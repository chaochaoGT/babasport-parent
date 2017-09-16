package com.ssm.test.service;

import com.ssm.core.dao.TestDao;
import com.ssm.core.pojo.TbUser;
import com.ssm.core.service.TestMyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * redis测试
 * Created by Administrator on 2017/8/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:config/redis.xml"})
public class RedisTest {


    @Test
    public void test1(){
        Jedis jedis=new Jedis("192.168.56.102",6379);
        Long incr = jedis.incr("ok");
        System.out.println("incr==" + incr);

    }

    @Autowired
    private  Jedis jedis;
    @Test
    public void test2(){
        Long incr = jedis.incr("ok");
        System.out.println("incr==" + incr);

    }



}
