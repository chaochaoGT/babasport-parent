package com.ssm.core.service;

import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;

import java.util.List;

/**
 * 商品库存的接口
 */
public interface SkuService {

    /**
     *根据商品ID查询所有的库存信息
     * @param sku
     * @return
     */
    List<SuperPojo> findAllByProductId(Sku sku);

    /**
     * 修改库存信息
     * @param sku
     */
    int toUpdateSku(Sku sku);
}
