package com.ssm.core.console;

import com.ssm.core.dictionary.Constants;
import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pagetools.PageHelper;
import com.ssm.core.pojo.Brand;
import com.ssm.core.service.BrandService;
import com.ssm.core.uploadfile.FileControlle;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;
import org.junit.experimental.runners.Enclosed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 中心控制器
 * Created by Administrator on 2017/8/13.
 */
@Controller
public class CenterAction {


    //主
    @RequestMapping("console/{pageName}.do")
    public String consoleShow(@PathVariable("pageName") String pageName) {
        return pageName;
    }

    //商品主页
    @RequestMapping("console/frame/{pageName}.do")
    public String consoleFrameShow(@PathVariable("pageName") String pageName) {
        return "frame/" + pageName;
    }

    //品牌管理
    @RequestMapping("console/brand/{pageName}.do")
    public String consoleBrandShow(@PathVariable("pageName") String pageName) {
        return "brand/" + pageName;

    }

    //商品管理
    @RequestMapping("console/product/{pageName}.do")
    public String consoleProductShow(@PathVariable("pageName") String pageName) {
        return "product/" + pageName;
    }

    //sku
    @RequestMapping("console/sku/{pageName}.do")
    public String consoleSkuShow(@PathVariable("pageName") String pageName) {
        return "sku/" + pageName;
    }

    //order
    @RequestMapping("console/order/{pageName}.do")
    public String consoleOrderShow(@PathVariable("pageName") String pageName) {
        return "order/" + pageName;
    }

    //position
    @RequestMapping("console/position/{pageName}.do")
    public String consolePositionShow(@PathVariable("pageName") String pageName) {
        return "position/" + pageName;
    }

    //type
    @RequestMapping("console/type/{pageName}.do")
    public String consoleTypeShow(@PathVariable("pageName") String pageName) {
        return "type/" + pageName;
    }

    //ad
    @RequestMapping("console/ad/ad_main")
    public String consoleAdmainShow(@PathVariable("pageName") String pageName) {
        return "ad/" + pageName;
    }


}
