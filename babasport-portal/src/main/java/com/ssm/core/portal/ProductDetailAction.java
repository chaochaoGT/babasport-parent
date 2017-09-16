package com.ssm.core.portal;

import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.BrandService;
import com.ssm.core.service.ProductService;
import com.ssm.core.service.SkuService;
import com.ssm.core.service.SolrService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 商品详情
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/8/20
 * Time: 17:15
 */

@Controller
public class ProductDetailAction {

    @Autowired
    private ProductService productService;



    //查看商品详情
    @RequestMapping("/detail")
    public String productdetail(Long productId,Model model){
        System.out.println("//查看商品详情");
        System.out.println("//查看商品详情product="+productId);
        //根据商品id查询商品信息;
        SuperPojo superPojo=productService.findProductById(productId);
       // Product product = (Product) superPojo.get("product");

        //根据商品id查询所有的sku,包括具体的颜色,价格,

        List<SuperPojo> skus = (List<SuperPojo>) superPojo.get("skus");
        //颜色去重复
        Map<Long,Object> mapColor=new HashMap<>();
        for (SuperPojo sup:skus){
            mapColor.put((Long) sup.get("color_id"),sup.get("colorName"));
        }

        superPojo.setProperty("mapColor",mapColor);

        model.addAttribute("superPojo",superPojo);
        System.out.println("//查看商品详情skus="+skus.size()+skus.get(0));
        return "product";
    }
}
