package com.ssm.core.service;

import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Color;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.SuperPojo;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

/**
 * 商品管理的接口
 * Created by Administrator on 2017/8/17.
 */
public interface ProductService {

    /**
     * 根据实例查询所有商品
     * @param product
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<Product> findAllByExample(Product product, Integer pageNum, Integer pageSize);


    /**
     * 查询所有颜色
     * @return
     */
    List<Color> findColorByExample();

    /**
     *
     * 添加商品
     * @param product
     */
    void saveProduct(Product product);

    /**
     * 删除多个商品
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 批量修改上下架或者删除商品信息
     * @param product
     * @param ids
     */
    void updateIsShow(Product product, String[] ids) throws IOException, SolrServerException;
 /**
     * mq批量修改上下架或者删除商品信息
     * @param isShow
     * @param ids
     */
    void updateIsShowUseMQ(Integer isShow, String ids) throws IOException, SolrServerException;

    /**
     * //根据商品id查询商品详情信息;
     * @param productId
     * @return Product
     */
    SuperPojo findProductById(Long productId);

    /**
     * //根据商品ids查询 多个商品信息;
     * @param productIds
     * @return Product
     */
    List<SuperPojo> findProductByIds(String productIds);


}
