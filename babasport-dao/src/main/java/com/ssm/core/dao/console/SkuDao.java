package com.ssm.core.dao.console;

import com.github.abel533.mapper.Mapper;
import com.ssm.core.pojo.Color;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品单件处理dao
 * Created by Administrator on 2017/8/13.
 */
@Repository
public interface SkuDao extends Mapper<Sku> {

    /**
     *根据商品ID查询所有的库存信息
     * @param id
     *
     * @return
     */
    List<SuperPojo> findAllByProductId(@Param("pid") Long id);

}
