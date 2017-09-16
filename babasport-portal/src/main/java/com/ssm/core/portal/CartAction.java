package com.ssm.core.portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ssm.core.Session.SessionTool;
import com.ssm.core.pojo.Cart;
import com.ssm.core.service.CartService;
import com.ssm.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 前端页面购物车控制中心
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2017/8/20
 * Time: 17:15
 */

@Controller
public class CartAction {
    @Autowired
    private CartService cartService;

    @RequestMapping("addCart")
    public String addCart(Long skuId, Integer amount, HttpServletRequest request, HttpServletResponse response){
        System.out.println("前端页面购物车skuId="+skuId+"*****amount="+amount);
       //客户未登录添加购物车sessioncart合并
        //获取sessioncart
        Cart cart1=getCartFromCookie(request);
        //获取新添加的商品数据
        productService.

        //未登录显示购物车

        //登录显示购物车,sessioncart和redis中合并后,去除
        //登录添加购物车,sessioncart和redis合并,去除
        return "cart";
    }


    /**
     * 从request中获取cart信息
     * @param request
     * @return
     */
    private Cart getCartFromCookie(HttpServletRequest request) {

        for (Cookie cookie : request.getCookies()) {
            if ("cart".equals(cookie.getName())){
                String cartJson = cookie.getValue();
                ObjectMapper om=new ObjectMapper();
                try {
                    Cart cart = om.readValue(cartJson, Cart.class);
                    return cart;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
