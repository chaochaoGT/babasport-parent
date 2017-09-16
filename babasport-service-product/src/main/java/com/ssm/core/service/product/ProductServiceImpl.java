package com.ssm.core.service.product;

import com.github.abel533.entity.Example;
import com.ssm.core.dao.console.ColorDao;
import com.ssm.core.dao.console.ProductDao;
import com.ssm.core.dao.console.SkuDao;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Color;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.util.*;

/**
 * 品牌业务层实现
 * Created by Administrator on 2017/8/13.
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    @Autowired
    private ColorDao colorDao;

     @Autowired
     private  SkuDao skuDao;

     @Autowired
     private SolrServer solrServer;

    //注入jedis
    @Autowired
    private Jedis jedis;

    @Autowired
    private JmsTemplate jmsTemplate;


    @Override
    public PageHelper.Page<Product> findAllByExample(Product product, Integer pageNum, Integer pageSize) {

        Example example =new Example(Product.class);
        //name条件不等于null,追加条件,否之查全部
        if(StringUtils.isNotBlank(product.getName())){
            example.createCriteria().andLike("name","%"+product.getName()+"%");
        }
        if (product.getIsShow()!=null){
            example.createCriteria().andEqualTo("isShow",product.getIsShow());
        }
        if (product.getBrandId()!=null){
            example.createCriteria().andEqualTo("brandId",product.getBrandId());
        }
        example.setOrderByClause("createTime desc");
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productDao.selectByExample(example);
        PageHelper.Page endPage = PageHelper.endPage();
        return endPage ;
    }

    @Override
    public List<Color> findColorByExample() {
        Example example=new Example(Color.class);
        example.createCriteria().andNotEqualTo("parentId",0);
        return colorDao.selectByExample(example);
    }

    @Override
    public void saveProduct(Product product) {
        //设置默认值
        if (product.getIsShow()==null){
            product.setIsShow(0);
        }
        if (product.getIsDel()==null){
            product.setIsShow(1);
        }
        if (product.getCreateTime()==null){
            product.setCreateTime(new Date());
        }

        Long productID = jedis.incr("productID");
//        Long productID = jedis.incrBy("productID",460);
        product.setId(productID);
        //添加商品
        productDao.insertSelective(product);
        System.out.println("获取回显的ID="+product.getId());

        //添加sku

        String colors = product.getColors();
        String sizes = product.getSizes();
        for (String color:colors.split(",")){
            for (String size:sizes.split(",")){
                Sku sku=new Sku();
                sku.setColorId(Long.parseLong(color));
                sku.setCreateTime(new Date());
                sku.setSize(size);
                sku.setProductId(product.getId());
                sku.setMarketPrice(1000.00f);
                sku.setPrice(800.00f);
                sku.setDeliveFee(20f);
                sku.setStock(0);
                sku.setUpperLimit(100);

                skuDao.insert(sku);
            }
        }
    }

    @Override
    public void deleteByIds(String ids) {
        String[] pids = ids.split(",");

        if(pids.length!=0){
            for (String id:pids){
                //通过id查询商品
                Product product = productDao.selectByPrimaryKey(Long.parseLong(id));
                System.out.println(product);
                product.setIsDel(0);
                //逻辑删除商品
                productDao.updateByPrimaryKeySelective(product);

            }
        }
    }



    @Override
    public void updateIsShow(Product product, String[] ids) throws IOException, SolrServerException {

        List idss=Arrays.asList(ids);
        Example example=new Example(Product.class);
        example.createCriteria().andIn("id", idss);
        if(ids.length!=0){
            //判断是否携带name条件,有的话设为null
            if (product.getName()!=null){
                product.setName(null);
            }
            //批量进行上架或者下架
            productDao.updateByExampleSelective(product,example);

        }
        //如果是上架的话,需要将商品信息添加到solr库中
        //Integer类型在数值大于200比较是结果false,需要equals比较
        if (product.getIsShow()==1){
            //商品上架, 查询除所有的product
            List<Product> productList = productDao.selectByExample(example);
            //提交solr的集合

            for (Product p:productList) {
                SolrInputDocument doc= new SolrInputDocument();
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

                solrServer.add(doc);
                solrServer.commit();
            }
        }
    }

    //用mq进行更新
    @Override
    public void updateIsShowUseMQ(Integer isShow, final String ids) throws IOException, SolrServerException {
        System.out.println("用mq进行更新"+ids);
        Product product = new Product();
        product.setIsShow(isShow);
        Example example=new Example(Product.class);
        example.createCriteria().andIn("id", Arrays.<Object>asList(ids));
        //商品更新
        productDao.updateByExampleSelective(product,example);

        //商品上架发送mq保存到solr
        if (isShow==1 && StringUtils.isNotBlank(ids)){
            jmsTemplate.send("productIds", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {

                    return session.createTextMessage(ids);
                }
            });
        }
    }

    @Override
    public SuperPojo findProductById(Long productId) {
        //获取商品
        Product product = productDao.selectByPrimaryKey(productId);

        //h获取商品详细列表

        List<SuperPojo> skus = skuDao.findAllByProductId(productId);

        SuperPojo superPojo=new SuperPojo();

        superPojo.setProperty("product",product);
        superPojo.setProperty("skus",skus);

        return superPojo;
    }

    @Override
    public List<SuperPojo> findProductByIds(String productIds) {
        String[] ids = productIds.split(",");
        List<SuperPojo> superPojos=new ArrayList<>();
        if (ids.length>0){
            for (String id:ids){
            //获取商品
            Product product = productDao.selectByPrimaryKey(Long.parseLong(id));

            //h获取商品详细列表
            List<SuperPojo> skus = skuDao.findAllByProductId(Long.parseLong(id));
            System.out.println("/品牌业务层实现/获取商品详细列表="+skus.size());
            SuperPojo superPojo=new SuperPojo();

            superPojo.setProperty("product",product);
            superPojo.setProperty("skus",skus);
            superPojos.add(superPojo);
            }
        }
        return superPojos;
    }
}
