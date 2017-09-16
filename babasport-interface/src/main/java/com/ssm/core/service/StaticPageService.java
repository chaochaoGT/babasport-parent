package com.ssm.core.service;

import org.springframework.web.context.ServletContextAware;

import java.util.Map;

/**
 * 创建静态页面的服务接口
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
public interface StaticPageService extends ServletContextAware {

    /**
     * 创建商品详情的静态页面
     * @param rootMap   封装 product skus color
     * @param id        商品id
     */
    void staticpegeService(Map<String ,Object> rootMap, String id);
}
