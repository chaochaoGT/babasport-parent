package com.ssm.core.pojo;

/**
 * 购买项的实体类
 * 即单个商品的具体购买信息
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
public class Item {

    /**
     * sku的id
     */
    private Long skuId;

    /**
     *购买数量
     */
    private Integer amount;

    /**
     * 单个商品的复杂的变异的实体类
     */
    private SuperPojo sku;


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public SuperPojo getSku() {
        return sku;
    }

    public void setSku(SuperPojo sku) {
        this.sku = sku;
    }
}
