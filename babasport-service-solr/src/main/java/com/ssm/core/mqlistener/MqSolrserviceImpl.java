package com.ssm.core.mqlistener;

import com.github.abel533.entity.Example;
import com.ssm.core.dao.console.ProductDao;
import com.ssm.core.dao.console.SkuDao;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.Sku;
import com.ssm.core.service.MqService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 商品上架的mq实现类
 * Created by IntelliJ IDEA.
 * User: Administrator
 */




public class MqSolrserviceImpl implements MqService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SkuDao skuDao;

    @Override
    public void onMessage(Message message) {

        ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
        try {
            String ids = textMessage.getText();
            System.out.println("商品上架的mq实现类solr_productIds:"+ids);
            List idss= Arrays.asList(ids.split(","));
            Example example=new Example(Product.class);
            example.createCriteria().andIn("id",idss);
            for (Product p : productDao.selectByExample(example)) {

                SolrInputDocument doc=new SolrInputDocument();
                //简单类型pid bid name
                doc.addField("id",p.getId());
                doc.addField("brandId",p.getBrandId());
                doc.addField("name_ik",p.getName());
                doc.addField("url",p.getImgUrl().split(",")[0]);

                //复杂类型 最低价格
                Example example1=new Example(Sku.class);
                example1.createCriteria().andEqualTo("productId",p.getId());
                example1.setOrderByClause("price asc");

                PageHelper.startPage(1,1);
                List<Sku> skus = skuDao.selectByExample(example1);
                PageHelper.Page<Sku> page = PageHelper.endPage();

                doc.addField("price",skus.get(0).getPrice());

                try {
                    solrServer.add(doc);
                    solrServer.commit();
                } catch (SolrServerException e) {
                   throw  new RuntimeException("商品上架的mq实现类"+e);
                } catch (IOException e) {
                    throw  new RuntimeException("商品上架的mq实现类"+e);
                }
            }
            ;
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
