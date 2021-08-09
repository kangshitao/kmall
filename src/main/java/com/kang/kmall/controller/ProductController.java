package com.kang.kmall.controller;


import com.kang.kmall.entity.Product;
import com.kang.kmall.entity.User;
import com.kang.kmall.service.CartService;
import com.kang.kmall.service.ProductCategoryService;
import com.kang.kmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("//product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    /**
     *
     * @param level  分类级别，one：一级分类，two：二级分类，three：三级分类
     * @param id  级别对应的id
     * @return
     */
    @GetMapping("/list/{level}/{id}")
    public ModelAndView productList(@PathVariable("level") String level,
                                    @PathVariable("id") Integer id,
                                    HttpSession session){
        /*
        RESTful风格，使用@PathVariable注解接收参数；
        url传参，则使用@PathParam接收参数。
         */
        ModelAndView mv = new ModelAndView();
        mv.setViewName("productList");
        mv.addObject("products",productService.findByCategotyId(level,id));
        mv.addObject("list",productCategoryService.getProductCategoryVO());
        User user = (User) session.getAttribute("user");
        mv.addObject("cartList",cartService.findAllCartByUserId(user.getId()));

        return mv;
    }

    @GetMapping("/productDetail/{id}")
    public ModelAndView productDetail(@PathVariable("id")Integer id,HttpSession session){
        ModelAndView mv = new ModelAndView();
        Product product = productService.getById(id);
        mv.setViewName("productDetail");
        mv.addObject("product",product);
        mv.addObject("list",productCategoryService.getProductCategoryVO());
        User user = (User) session.getAttribute("user");
        mv.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return mv;
    }



}

