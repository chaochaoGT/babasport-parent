package com.ssm.core.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 自定义session工具类 主要是分配maosessionid
 *
 * @author Administrator
 *
 */
public class SessionTool {

    /**
     * 获得自定义sessionID，并在初次访问时，分配maosessionid，并将maosessionid写入到浏览器的cookie中
     *
     * @return
     */
    public static String getSessionID(HttpServletRequest request,
                                      HttpServletResponse response) {
        // 1、从cookie中取出maosessionid
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            // 判断cookie中是否有maosessionid
            for (Cookie cookie : cookies) {
                if ("maosessionid".equals(cookie.getName())) {
                    // 如果有，则直接取出来
                    return cookie.getValue();
                }
            }
        }

        // 如果没有maosessionid，则重新为该浏览器创建一个
        String maosessionid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("重新为该浏览器创建maosessionid");
        Cookie cookie = new Cookie("maosessionid", maosessionid);
        // 设置cookie存活时间
        cookie.setMaxAge(-1);

        // 设置路径 如果不设置，端口后面的目录名称（xxx:8080/login）会对cookie存储照成影响
        cookie.setPath("/");

        // 设置二级跨域，由于本次课程中都是localhost，所有无需设置，但是项目正式上限后需要设置该项
        // cookie.setDomain(".babasport.com");

        // 把cookie写回浏览器客户端
        response.addCookie(cookie);

        return maosessionid;
    };

    public static void main(String[] args) {
        String maosessionid = UUID.randomUUID().toString();
        System.out.println(maosessionid);
    }

}
