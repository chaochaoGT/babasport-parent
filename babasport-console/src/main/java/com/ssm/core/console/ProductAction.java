package com.ssm.core.console;

import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.pojo.Color;
import com.ssm.core.pojo.Product;
import com.ssm.core.service.BrandService;
import com.ssm.core.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌控制器
 * Created by Administrator on 2017/8/13.
 */
@Controller
public class ProductAction {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    //查询所有品牌信息/product/product/list.do
    @RequestMapping("console/product/list.do")
    public String consoleBrandList(Product product, Integer pageSize,
                                   Integer pageNum, Model model) {

        String name = Encoding.encodeGetRequest(product.getName());
        if (StringUtils.isNotBlank(name)){
            product.setName(name);
        }
        //回显页面查询条件
        model.addAttribute("name",name);
        model.addAttribute("isShow",product.getIsShow());
        model.addAttribute("brandId",product.getBrandId());
        // 将查询出来的商品集合传递给页面
        PageHelper.Page<Product> productPage = productService.findAllByExample(product, pageNum, pageSize);
        System.out.println("查询list结果=" + productPage.getResult().size());
        model.addAttribute("productPage",productPage);


        //查询商品品牌
        List<Brand> brands= brandService.findUseByExample();
        model.addAttribute("brands",brands);
        return "product/list";
    }

    //前往添加商品页面
    @RequestMapping("console/product/showAdd.do")

    public  String showEidt(Model model) {
        Map<String,List<?>> map=new HashMap<>();
        //查询颜色
        List<Color> colors= productService.findColorByExample();

        System.out.println("查询颜色"+colors.size());
            map.put("colors",colors);
        //查询商品品牌
        List<Brand> brands= brandService.findUseByExample();
        map.put("brands",brands);
        //查询商品类型
        model.addAllAttributes(map);
        return "product/add";
    }

    //添加商品console/product/doEidt.do
    @RequestMapping("console/product/doAdd.do")
    public String doEidt(Product product) {
        product.setName(Encoding.encodeGetRequest(product.getName()));
        product.setDescription(Encoding.encodeGetRequest(product.getDescription()));
        product.setPackageList(Encoding.encodeGetRequest(product.getPackageList()));
        System.out.println("提交表单去修改品牌" + product.getName());
        productService.saveProduct(product);

        return "redirect:list.do";
    }

    //批量修改上下架或者删除商品信息
    @RequestMapping("console/product/updateIsShow.do")
    public String updateIsShow(Product product, Integer pageSize,
                              Integer pageNum,String ids){
        try {
//            productService.updateIsShow(product,ids);
            productService.updateIsShowUseMQ(product.getIsShow(),ids);
            String url="redirect:list.do?pageNum="+pageNum+"&pageSize="+pageSize;
            if (StringUtils.isNotBlank(product.getName())){
                url+="&name="+product.getName();
            }
            if (product.getBrandId()!=null){
                url+="&brandId="+product.getBrandId();
            }
            if (product.getIsShow()!=null){
                url+="&isShow="+product.getIsShow();
            }

            System.out.println("批量修改上下架或者删除商品信息");
            System.out.println(url);
            return url;
        } catch (Exception e) {
            throw new RuntimeException("批量修改上下架或者删除商品信息"+e);
        }
    }

}
