package com.ssm.core.service;

import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.SuperPojo;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * solr服务的实现类
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/8/20
 * Time: 21:39
 */
@Service("solrService")
public class SolrServiceImpl implements SolrService {

    @Autowired
    private SolrServer solrServer;
    //查询商品
    @Override
    public PageHelper.Page<SuperPojo> findProductFromSolr(String keyword, Integer pageNum, Integer pageSize, String solt, Float pa, Float pb, Long brandId) throws SolrServerException {
        PageHelper.Page page = new PageHelper.Page(pageNum, pageSize);

        SolrQuery solrParams = null;
        //设置查询字段
        if(StringUtils.isBlank(keyword)){
            solrParams = new SolrQuery("name_ik:"+"2016");
        }else{
            solrParams = new SolrQuery("name_ik:"+keyword);
        }

        // 设置精确查询条件
        if (brandId!=null){
            solrParams.addFilterQuery("brandId:"+brandId);
        }
        if (pa!=null){
            if (pb==-1){
                solrParams.addFilterQuery("price:[" + pa + " TO *]");
            }else{
                solrParams.addFilterQuery("price:[" + pa + " TO "+pb+"]");
            }
        }

        //设置分页查询条数
        solrParams.setStart(page.getStartRow());
        solrParams.setRows(page.getPageSize());

        if (StringUtils.isNotBlank(solt)){
            //设置排序
            SolrQuery.SortClause sortClause = new SolrQuery.SortClause(solt.split(" ")[0],solt.split(" ")[1]);
//        SolrQuery.SortClause sortClause = new SolrQuery.SortClause("price","desc");

            solrParams.setSort(sortClause);
        }

        //solrParams.setRows(100);
        //开启高亮
        solrParams.setHighlight(true);
        solrParams.addHighlightField("name_ik");
        //设置高亮字段格式
        solrParams.setHighlightSimplePre("<span style='color:red'>");//前缀
        solrParams.setHighlightSimplePost("</span>");//后缀

        //开始查询
        QueryResponse response = solrServer.query(solrParams);
        //获取高亮集合
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        //获取查询结果
        SolrDocumentList documents = response.getResults();
        //获取总量
        long numFound = documents.getNumFound();
       //存入分页
        page.setTotal(numFound);
        //
        List<SuperPojo> superPojoList=new ArrayList<>();
        for (SolrDocument document:documents) {
            SuperPojo superPojo=new SuperPojo();
            String id = (String) document.getFieldValue("id");
            superPojo.setProperty("id",id);
            superPojo.setProperty("brandId",document.getFieldValue("brandId"));
            //获取高亮name
            Map<String, List<String>> stringListMap = highlighting.get(id);
            String name = stringListMap.get("name_ik").get(0);

            superPojo.setProperty("name",name);
            superPojo.setProperty("imgUrl",document.getFieldValue("url"));
            superPojo.setProperty("price",document.getFieldValue("price"));
            superPojoList.add(superPojo);
        }
        System.out.println("solr服务的实现类:"+superPojoList.size());
        page.setResult(superPojoList);
        return page;
    }


}
