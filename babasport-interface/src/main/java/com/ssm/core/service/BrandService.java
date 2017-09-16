package com.ssm.core.service;

import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;

import java.util.List;

/**
 * 商品接口
 */
public interface BrandService {

    /**
     * 按实例查询商品
     * @param brand
     * @param pageNum
     *@param pageSize @return
     */
    PageHelper.Page<Brand> findAllByExample(Brand brand, Integer pageNum, Integer pageSize);

    /**
     * 根据BrandID进行查询数据回显
     * @param id
     * @return
     */
    Brand findByID(Long id);

    /**
     * 根据brandID进行修改品牌信息
     * @param brand
     */
    void updateBrandById(Brand brand);

    /**
     *根据brandIDs进行删除品牌信息s
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     *  查询正常可用的所有品牌
     */
    List<Brand> findUseByExample();

    /**
     * 添加品牌
     * @param brand
     */
    void saveBrand(Brand brand);

    /**
     * 从redis查询品牌信息
     * @return
     */
    List<Brand> findBrandFromRedis();
}
