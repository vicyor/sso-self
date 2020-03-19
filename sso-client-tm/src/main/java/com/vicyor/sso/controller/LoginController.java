package com.vicyor.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 作者:姚克威
 * 时间:2020/3/18 15:32
 **/
@Controller
public class LoginController {
    @RequestMapping("/")
    public String index(){
        return "tmall";
    }
}
