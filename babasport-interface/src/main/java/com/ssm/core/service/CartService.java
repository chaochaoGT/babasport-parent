package com.ssm.core.service;

import com.ssm.core.pojo.Cart;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;

import java.util.List;

/**
 * 购物车的接口
 */
public interface CartService {

    /**
     * 根据
     * @param skuId
     * @param amount
     * @return
     */
    public Cart findPorductAndSku(Long skuId,Integer amount);
}
