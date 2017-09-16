package com.ssm.core.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;

/**
 * 创建静态页面的实现类
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService {

    private  ServletContext servletContext;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    //创建静态页面
    @Override
    public void staticpegeService(Map<String, Object> rootMap, String id) {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            //获取模板
            Template template = configuration.getTemplate("product.html");

            //设置静态页面的位置
            File filedir=new File(servletContext.getRealPath("/")+"/html/product/");
            if (!filedir.exists()){
                filedir.mkdirs();
            }
            System.out.println("创建静态页面:"+filedir+"//"+id+".html");
//            Writer out=new FileWriter(filedir+"//"+id+".html");
            Writer out = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(new File(filedir+"//"+id+".html")),"UTF-8"));
            template.process(rootMap,out);

        } catch (IOException |TemplateException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
