package com.ssm.core.console;

import com.ssm.core.pojo.Brand;
import com.ssm.core.pojo.Sku;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌控制器
 * Created by Administrator on 2017/8/13.
 */
@Controller
public class SkuAction {

    @Autowired
    private SkuService skuService;


    //查询当前商品下所有库存的信息console/sku/list.do
    @RequestMapping("console/sku/list.do")
    public String consoleBrandList(Sku sku,  Model model) {

        //查询当前商品旗下的所有库存
        List<SuperPojo> skuList = skuService.findAllByProductId(sku);
        System.out.println("查询当前商品下所有库存的信息=" + skuList.size());
        model.addAttribute("skuList", skuList);
        return "sku/list";
    }

    //修改库存信息
    @RequestMapping("console/sku/toUpdateSku.do")
    @ResponseBody
    public  String toUpdateSku(Sku sku) {
        System.out.println("修改库存信息"+sku);
            skuService.toUpdateSku(sku);
        return null;
    }
//
//    //添加商品console/product/doEidt.do
//    @RequestMapping("product/product/doAdd.do")
//    public String doEidt(Product product) {
//        product.setName(Encoding.encodeGetRequest(product.getName()));
//        product.setDescription(Encoding.encodeGetRequest(product.getDescription()));
//        product.setPackageList(Encoding.encodeGetRequest(product.getPackageList()));
//        System.out.println("提交表单去修改品牌" + product.getName());
//        productService.saveProduct(product);
//
//        return "redirect:list.do";
//    }

//    //根据brandids删除
//    @RequestMapping("product/brand/deleteByids.do")
//    public String deleteByids(String name, Integer isDisplay, Integer pageSize,
//                              Integer pageNum,String ids){
//        brandService.deleteByIds(ids);
//
//        System.out.println("//根据brandids删除="+name+"==isDisplay=="+isDisplay+
//                "pageNum="+pageNum+"pageSize="+pageSize);
//        return "redirect:list.do?name="+name+"&isDisplay="+(isDisplay==null?"":isDisplay)+"&pageNum="+pageNum+
//                "&pageSize="+pageSize;
//    }

}
