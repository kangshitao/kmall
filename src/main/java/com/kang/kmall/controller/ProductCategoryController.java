package com.kang.kmall.controller;


import com.kang.kmall.entity.User;
import com.kang.kmall.service.CartService;
import com.kang.kmall.service.ProductCategoryService;
import com.kang.kmall.viewObject.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Controller
@RequestMapping("//productCategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",productCategoryService.getProductCategoryVO());
        User user = null;
        try {
            user = (User) session.getAttribute("user");
            modelAndView.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        } catch (Exception e) {
            //如果用户没有登陆，可以访问商品浏览页。传入空值
            modelAndView.addObject("cartList",new ArrayList<CartVO>());
        }
        return modelAndView;
    }
}

