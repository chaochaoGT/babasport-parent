package com.ssm.core.console;

import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品牌控制器
 * Created by Administrator on 2017/8/13.
 */
@Controller
public class BrandAction {

    @Autowired
    private BrandService brandService;

    //查询所有品牌信息/product/brand/list.do
    @RequestMapping("console/brand/list.do")
    public String consoleBrandList(String name, Integer isDisplay, Integer pageSize,
                                   Integer pageNum, Model model) {
        Brand brand = new Brand();
        name = Encoding.encodeGetRequest(name);
        brand.setName(name);
        brand.setIsDisplay(isDisplay);
        PageHelper.Page<Brand> brandPage = brandService.findAllByExample(brand, pageNum, pageSize);
        System.out.println("查询list结果=" + brandPage.getResult().size());
        // 将查询出来的品牌集合传递给页面
        model.addAttribute("brandPage", brandPage);
        model.addAttribute("name", name);
        model.addAttribute("isDisplay", isDisplay);

        return "brand/list";
    }

    //根据brandID去修改页面
    @RequestMapping("console/brand/showEdit.do")
    public String showEidt(Long brandID, Model model) {

        Brand brand = brandService.findByID(brandID);
        System.out.println("根据brandID去修改页面:" + brand.getName() + brand.getImgUrl());
        model.addAttribute("brand", brand);
        return "brand/edit";
    }

    //添加页面
    @RequestMapping("console/brand/doAdd.do")
    public String addDo(Brand brand) {

        brand.setName(Encoding.encodeGetRequest(brand.getName()));
        brand.setDescription(Encoding.encodeGetRequest(brand.getDescription()));
        System.out.println("提交表单去添加品牌" + brand.getName());
        brandService.saveBrand(brand);
        return "redirect:list.do";
    }

    //根据提交表单去修改品牌console/brand/doEidt.do
    @RequestMapping("console/brand/doEdit.do")
    public String doEidt(Brand brand) {
        brand.setName(Encoding.encodeGetRequest(brand.getName()));
        brand.setDescription(Encoding.encodeGetRequest(brand.getDescription()));
        System.out.println("提交表单去修改品牌" + brand.getName());
        brandService.updateBrandById(brand);

        return "redirect:list.do";
    }

    //根据brandids删除
    @RequestMapping("console/brand/deleteByids.do")
    public String deleteByids(String name, Integer isDisplay, Integer pageSize,
                              Integer pageNum,String ids){
        brandService.deleteByIds(ids);

        System.out.println("//根据brandids删除name="+name+"isDisplay=="+isDisplay+
                "pageNum="+pageNum+"pageSize="+pageSize);
        return "redirect:list.do?name="+name+"&isDisplay="+(isDisplay==null?"":isDisplay)+"&pageNum="+pageNum+
                "&pageSize="+pageSize;
    }

}
