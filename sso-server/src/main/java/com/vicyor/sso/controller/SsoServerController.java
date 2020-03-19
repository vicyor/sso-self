package com.vicyor.sso.controller;

import com.vicyor.sso.db.MockDB;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者:姚克威
 * 时间:2020/3/18 13:10
 **/
@Controller
public class SsoServerController {
    @RequestMapping("/toLogin")
    public String index() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("password")String password,@RequestParam("redirectUrl") String redirectUrl, HttpSession session,Model model) {
        System.out.println("Debug:username=>" + username + " - password=>" + password);
        //模拟数据
        if (username.equals("admin") && password.equals("123456")) {
            //给用户返回token令牌
            String token = UUID.randomUUID().toString();
            System.out.println("生成token成功=>" + token);
            //将token存到session中
            session.setAttribute("token", token);
            //将token入库
            Pattern pattern=Pattern.compile(".*//(.*?)/(.*?)/.*");
            Matcher matcher = pattern.matcher(redirectUrl);
            matcher.find();
            MockDB.putRelation(token,matcher.group(2));
            //返回给客户端
            model.addAttribute("token",token);
            return "redirect:"+redirectUrl;
        }
        System.out.println("用户名,密码不正确。");
        return "forward:toLogin";
    }

    /**
     * 去其它的系统检测是否登录
     * 检测login
     */
    @RequestMapping("/checkLogin")
    public String checkLogin(@RequestParam("redirectUrl")  String redirectUrl, HttpSession session, Model model) {
        //判断用户是否登录,是否拥有全局会话
        String token = (String) session.getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            //会在url的queryStr上拼接redirectUrl
            model.addAttribute("redirectUrl", redirectUrl);
            //去登录页
            return "forward:toLogin";
        } else {

            Pattern pattern=Pattern.compile(".*//(.*?)/(.*?)/.*");
            Matcher matcher = pattern.matcher(redirectUrl);
            matcher.find();
            //将关系存入db
            MockDB.putRelation(token,matcher.group(2));
            //返回token
            model.addAttribute("token",token);
            //重定向会redirectURL
            return "redirect:" + redirectUrl;
        }
    }
    @RequestMapping("/verify")
    @ResponseBody
    public String verifyTicket(@RequestParam String  token,@RequestParam("app")String app,HttpSession session){
        List<String> apps = MockDB.getRelation(token);
        if(apps.contains(app)){
            return "OK";
        }else{
            return "wrong";
        }
    }
}
