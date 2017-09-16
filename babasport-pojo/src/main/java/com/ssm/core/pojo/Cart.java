package com.ssm.core.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的实体类
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
public class Cart {

    /**
     *购买项的集合
     */
    private List<Item> items=new ArrayList<Item>();


    /**
     * 添加商品同类商品合并
     */
    public void addItem(Item item){
        for (Item it:items) {
            if (item.getSkuId()==it.getSkuId()){
                it.setAmount(it.getAmount()+item.getAmount());
                return ;
            }
        }
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
