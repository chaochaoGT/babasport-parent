package com.ssm.core.service.product;

import com.github.abel533.entity.Example;
import com.ssm.core.dao.console.SkuDao;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 库存业务层实现
 * Created by Administrator on 2017/8/13.
 */
@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;

    @Override
    public List<SuperPojo> findAllByProductId(Sku sku) {
//        Example example =new Example(Sku.class);
//        example.createCriteria().andEqualTo("productId" ,
//                sku.getProductId());
       // PageHelper.startPage(pageNum,pageSize);
        //封装颜色
        List<SuperPojo> skus = skuDao.findAllByProductId(sku.getProductId());
     //   PageHelper.Page<Sku> page = PageHelper.endPage();
        System.out.println("库存业务层实现="+skus.size());

        System.out.println(skus.get(0));
        return skus;
    }

    @Override
    public int toUpdateSku(Sku sku) {
        int i = skuDao.updateByPrimaryKeySelective(sku);
        return i;
    }
}
