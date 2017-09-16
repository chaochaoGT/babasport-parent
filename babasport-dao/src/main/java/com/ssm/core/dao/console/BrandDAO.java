package com.ssm.core.dao.console;

import com.github.abel533.mapper.Mapper;
import com.ssm.core.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandDAO extends Mapper<Brand> {

    /**
     * 按实例查询商品
     * @param brand brand对象
     * @return brand集合
     */
    List<Brand> findAllByExample( Brand brand);

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
}
