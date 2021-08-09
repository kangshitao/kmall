package com.kang.kmall.controller;

import com.kang.kmall.entity.Product;
import com.kang.kmall.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author Kangshitao
 * @date 2021年7月31日 上午11:29
 */
@Controller
public class RedirectHandler {

    //将所有url都映射为请求
//    @GetMapping("/{url}")
//    public String redirect(@PathVariable("url") String  url){
//        return url;
//    }

    @GetMapping(value = {"/", "main"})
    public String index() {
        return "redirect:/productCategory/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
