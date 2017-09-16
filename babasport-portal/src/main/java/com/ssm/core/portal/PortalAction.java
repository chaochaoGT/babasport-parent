package com.ssm.core.portal;

import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.BrandService;
import com.ssm.core.service.SolrService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 前端页面中心控制器
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/8/20
 * Time: 17:15
 */

@Controller
public class PortalAction {

    @Autowired
    private SolrService solrService;

    @Autowired
    private BrandService brandService;
    //首页
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("search")
    public String search(String keyword,String solt, Integer pageNum, Integer pageSize,
                         Float pa,Float pb, Long brandId, Model model){
        keyword= Encoding.encodeGetRequest(keyword);
        model.addAttribute("keyword",keyword);

        PageHelper.Page<SuperPojo> superPojo= null;
        try {

            //solr查询商品信息
            superPojo = solrService.findProductFromSolr(keyword,pageNum,pageSize,solt,pa,pb,brandId);
            //回显搜索框条件
            model.addAttribute("solt1",solt);
            if (solt.equals("price asc")){
                solt="price desc";
            }else {
                solt="price asc";
            }

            //redis查询品牌信息
            List<Brand> brands = brandService.findBrandFromRedis();
            model.addAttribute("brands",brands);

            //回显价格调节
            model.addAttribute("solt",solt);
            //回显价格区间,和brandId
            model.addAttribute("pa",pa);
            model.addAttribute("pb",pb);
            model.addAttribute("brandId",brandId);

            //回显查询条件map
            Map<String,Object> map =new HashMap<>();
            if (pa!=null&&pb!=null){
                model.addAttribute("price",1);
                if (pb==-1){
                    map.put("价格:",pa+"以上");
                }else {
                    map.put("价格:",pa+"-"+pb);
                }
            }

            if (brandId!=null){
                for (Brand b:brands) {
                    if (b.getId()==brandId){
                        map.put("品牌:",b.getName());
                        break;
                    }
                }
            }
            model.addAttribute("map",map);
            System.out.println("前端页面中心控制器List<SuperPojo>:"+superPojo.getResult().size());
            System.out.println("前端页面中心控制器List<Brand> brands:"+brands.size());
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        model.addAttribute("superPojo",superPojo);

        //solr查询结果
        return "search";
    }

}
