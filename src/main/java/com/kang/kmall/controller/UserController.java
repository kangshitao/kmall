package com.kang.kmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.User;
import com.kang.kmall.service.CartService;
import com.kang.kmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Controller
@RequestMapping("//user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    //注册
    @PostMapping("/register")
    public String register(User user, Model model){  //直接将传过来的参数封装为实体类，前提是表单项名和属性名相同
        boolean save = false;
        //可以使用ajax进行实时判断
        try {
            save = userService.save(user);
        } catch (Exception e) {
            //如果user插入失败，返回错误信息
            model.addAttribute("error","昵称已被占用");
            return "register";
        }
        if(save) return "login";
        return "register";
    }

    /**
     * 登陆
     * @param loginName
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name",loginName);  //这里的列名应该和数据库中的列名保持一致
        queryWrapper.eq("password",password);
        User user = userService.getOne(queryWrapper);
        if(user != null){
            session.setAttribute("user",user);
            return "redirect:/productCategory/list";
        }else{
            return "login";
        }
    }

    /**
     * 注销
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return modelAndView;
    }

    @GetMapping("/orderList")
    public ModelAndView orderList(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return modelAndView;
    }

    @GetMapping("/userAddressList")
    public ModelAndView userAddressList(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return modelAndView;
    }
}

