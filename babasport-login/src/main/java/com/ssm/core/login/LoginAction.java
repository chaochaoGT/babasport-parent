package com.ssm.core.login;

import com.ssm.core.Session.SessionTool;
import com.ssm.core.md5tool.Encryption;
import com.ssm.core.pagetools.Encoding;
import com.ssm.core.pojo.Buyer;
import com.ssm.core.pojo.Product;
import com.ssm.core.service.SessionService;
import com.ssm.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前端登录的控制器
 * Created by IntelliJ IDEA.
 * User: Administrator
 */
@Controller
public class LoginAction {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;


    //登录显示页面
    @RequestMapping(value = "login.aspx", method = RequestMethod.GET)
    public String showLoginPage(Model model,String returnUrl) {
            model.addAttribute("returnUrl",returnUrl);
        System.out.println("    //登录显示页面"+returnUrl);
        return "login";
    }

    //登录提交页面
    @RequestMapping(value = "login.aspx", method = RequestMethod.POST)
    public String doLogin(Model model, String returnUrl, String username, String password, HttpServletRequest request,HttpServletResponse response) {
        System.out.println("登录提交页面username:" + username + "===password:" + password);
        System.out.println("登录提交页面returnUrl:" + returnUrl);

        //验证码验证
        //用户之恋登录页面
        if (username != null) {
            //通过username获取用户,
            Buyer buyer = userService.findBuyerByUserName(username);
            if (buyer != null) {
                if (password != null && buyer.getPassword().equals(Encryption.encrypt(password))) {
                    //验证成功,开始登录
                    sessionService.addLoginUserToRedis(SessionTool.getSessionID(request,response),buyer.getUsername());
                    System.out.println("验证成功,开始登录");

                    if (returnUrl == null || returnUrl.length() < 0) {
                        return "redirect:http://localhost:8083/";
                    } else {
                        return "redirect:" + Encoding.encodeGetRequest(returnUrl);
                    }
                } else {
                    //密码错误,请重新输入
                    model.addAttribute("error", "密码错误,请重新输入");
                }

            } else {
                //用户不存在
                model.addAttribute("error", "用户不存在,请注册");
            }
        } else {
            //用户名不能为空;
            model.addAttribute("error", "用户名不能为空");
        }
        return "login";
    }


    //判断是否登录页面
    @RequestMapping(value = "islogin.aspx", method = RequestMethod.GET)
    @ResponseBody
    public MappingJacksonValue isLogin( String callback, HttpServletRequest request,HttpServletResponse response) {
        System.out.println("判断是否登录页面");
        //获取cookie中的maoseid
        String sessionID = SessionTool.getSessionID(request, response);

        String username = sessionService.getLoginUserFromRedis(sessionID);
        System.out.println("判断是否登录页面username="+sessionID+username);
        MappingJacksonValue jacksonValue=new MappingJacksonValue(username);
            jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }
}
