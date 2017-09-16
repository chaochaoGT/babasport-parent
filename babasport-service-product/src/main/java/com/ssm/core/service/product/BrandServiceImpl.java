package com.ssm.core.service.product;

import com.ssm.core.dao.console.BrandDAO;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 品牌业务层实现
 * Created by Administrator on 2017/8/13.
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

   @Autowired
   private BrandDAO brandDAO;

   @Autowired
   private Jedis jedis;
    @Override
    public  PageHelper.Page<Brand> findAllByExample(Brand brand, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
            List<Brand> brands=brandDAO.findAllByExample(brand);
        PageHelper.Page page = PageHelper.endPage();
        return page;
    }

    //查询
    @Override
    public Brand findByID(Long id) {
        return brandDAO.findByID(id);
    }

    //修改
    @Override
    public void updateBrandById(Brand brand) {
        brandDAO.updateBrandById(brand);
         jedis.hset("brand", String.valueOf(brand.getId()),brand.getName());
    }
    //删除
    @Override
    public void deleteByIds(String ids) {
        brandDAO.deleteByIds(ids);
    }

    @Override
    public List<Brand> findUseByExample() {
        return brandDAO.findUseByExample();
    }

    @Override
    public void saveBrand(Brand brand) {
        brandDAO.insertSelective(brand);
    }

    //reidsc查询品牌
    @Override
    public List<Brand> findBrandFromRedis() {
        Map<String,String > brands=jedis.hgetAll("brand");
        List<Brand> bl=new ArrayList<>();
        for (Map.Entry<String, String> b : brands.entrySet()) {
            Brand brand=new Brand();
            brand.setId(Long.valueOf(b.getKey()));
            brand.setName(b.getValue());
            bl.add(brand);
        }

        return bl;
    }
}
