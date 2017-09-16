package com.ssm.core.service;

import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.SuperPojo;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * solr搜索接口
 */
public interface SolrService {

    /**
     * 从solr进行搜索商品信息
     * @param keyword
     * @param pa
     *@param pb
     * @param brandId @return
     */
    PageHelper.Page<SuperPojo> findProductFromSolr(String keyword, Integer pageNum, Integer pageSize, String solt, Float pa, Float pb, Long brandId) throws SolrServerException;

  
}
