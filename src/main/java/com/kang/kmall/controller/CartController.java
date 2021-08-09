package com.kang.kmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kang.kmall.entity.Cart;
import com.kang.kmall.entity.User;
import com.kang.kmall.service.CartService;
import com.kang.kmall.viewObject.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Controller
@RequestMapping("//cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 往购物车中添加内容
     * @param productId
     * @param price
     * @param quantity
     * @param session
     * @return
     */
    @GetMapping("/add/{productId}/{price}/{quantity}")
    public String add(@PathVariable("productId") Integer productId,
                            @PathVariable("price") Float price,
                            @PathVariable("quantity") Integer quantity,
                            HttpSession session){


        User user = (User) session.getAttribute("user");

        //先查询当前用户的购物车中有没有当前产品
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        queryWrapper.eq("product_id",productId);
        Cart one = cartService.getOne(queryWrapper);
        if(one != null){  //如果有当前产品，则更新即可
            int temp = one.getQuantity()+quantity;
            one.setQuantity(temp);
            one.setCost(price*temp);
            one.setUserId(user.getId());
            cartService.updateById(one);
        }else{ //如果没有当前产品，则新建cart对象，并add到数据库
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cart.setCost(price*quantity);
            cart.setUserId(user.getId());
            try{
                cartService.save(cart);
            }catch (Exception e){
                return "redirect:/productCategory/list";
            }
        }

        return "redirect:/cart/findAllCart";
    }


    /**
     * 根据id删除购物车中的内容
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteCartByid(@PathVariable("id") Integer id){
        cartService.removeById(id);
        return "redirect:/cart/findAllCart";
    }

    /**
     * 查询当前用户的所有购物车内容
     * @param session
     * @return
     */
    @GetMapping(value = {"/findAllCart","/settlement1"})
    public ModelAndView findAllCart(HttpSession session, HttpServletResponse response){
        //设置页面禁止缓存，确保页面回退时，会重新加载页面
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Controll","no-cache");
        response.setHeader("pragma","no-cache");

        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        mv.setViewName("settlement1");
        mv.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return mv;
    }

    /**
     * 修改购物车内容时，将其更新到数据库
     * @param id
     * @param quantity
     * @param cost
     * @return
     */
    @ResponseBody
    @PostMapping("/updateCart/{id}/{quantity}/{cost}")
    public String updateCart(@PathVariable("id") Integer id,
                             @PathVariable("quantity") Integer quantity,
                             @PathVariable("cost") Float cost){

        Cart cart = cartService.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        boolean isSuccess = cartService.updateById(cart);
        if(isSuccess){
            return "success";
        }else {
            return "fail";
        }
    }

}

