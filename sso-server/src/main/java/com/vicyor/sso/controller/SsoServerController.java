package com.vicyor.sso.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 作者:姚克威
 * 时间:2020/3/18 13:10
 **/
@Controller
public class SsoServerController {
    @RequestMapping("/")
    public String index(){
        return "login";
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(String username,String password){
        System.out.println("Debug:username=>"+username+" - password=>"+password);
        return "login";
    }
    /**
     * 去其它的系统检测是否登录
     * 检测login
     *
     */
    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model){
        //判断用户是否登录,是否拥有全局会话
        String token = (String) session.getAttribute("token");
        if(StringUtils.isEmpty(token)){
            //将重定向url设置到request中
            model.addAttribute("redirectUrl",redirectUrl);
            //去登录页
            return "forward:login";
        }else{
            //重定向会redirectURL
            return "redirect:"+redirectUrl;
        }
    }
}
