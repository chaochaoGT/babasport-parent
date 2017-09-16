package com.ssm.core.mqlistener;

import com.ssm.core.pojo.Product;
import com.ssm.core.pojo.SuperPojo;
import com.ssm.core.service.MqService;
import com.ssm.core.service.ProductService;
import com.ssm.core.service.StaticPageService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.*;

/**
 * 创建静态页面的监听器
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
public class MqStaticPageListener implements MqService {
    @Autowired
    private ProductService productService;
    @Autowired
    private StaticPageService staticPageService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;

        try {
            String ids = textMessage.getText();
            //根据i获取所有的product
            System.out.println("创建静态页面的监听器ids"+ids);

            List<SuperPojo> superPojoList = productService.findProductByIds(ids);

            System.out.println("创建静态页面监听器superPojoList"+superPojoList.size());
            for (SuperPojo superPojo:superPojoList){



                Product product = (Product) superPojo.get("product");

                List skus = (List) superPojo.get("skus");

                //颜色去重复使用set
                Set<SuperPojo> colors=new HashSet<>();
                for (Object sups:skus){
                    SuperPojo sup=(SuperPojo)sups;
                    SuperPojo color = new SuperPojo();
                    color.setProperty("id", sup.get("color_id"));
                    color.setProperty("name", sup.get("colorName"));

                    // 将颜色对象添加到hm集合中，利用hm集合来去除重复
                    colors.add(color);
                }

                superPojo.setProperty("colors",colors);


                //创建静态页面
                System.out.println("//创建静态页面"+superPojo);

                //封装superprpojo
                Map<String ,Object> rootmap=new HashMap<>();
                rootmap.put("superPojo",superPojo);
                staticPageService.staticpegeService(rootmap, product.getId()+"");

            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
