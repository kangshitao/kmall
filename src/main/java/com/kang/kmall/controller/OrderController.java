package com.kang.kmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Cart;
import com.kang.kmall.entity.Orders;
import com.kang.kmall.entity.User;
import com.kang.kmall.service.CartService;
import com.kang.kmall.service.OrderService;
import com.kang.kmall.service.UserAddressService;
import com.kang.kmall.viewObject.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Controller
@RequestMapping("//order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserAddressService addressService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/computeOrder")
    public ModelAndView computeOrder(HttpSession session, HttpServletResponse response) {
        //设置页面禁止缓存，确保页面回退时，会重新加载页面
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Controll", "no-cache");
        response.setHeader("pragma", "no-cache");

        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");
        mv.setViewName("settlement2");
        mv.addObject("cartList", cartService.findAllCartByUserId(user.getId()));
        //生成订单号
        mv.addObject("orderNum", UUID.randomUUID().toString().replace("-", ""));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        mv.addObject("addressList", addressService.list(queryWrapper));
        return mv;
    }

    @PostMapping("/confirmOrder")
    public ModelAndView confirmOrder(Orders order, HttpSession session) {
        /*
        1、为了防止订单重复提交，可以为每个订单设置订单号，每次提交时，查询数据库中是否存在，
        如果已存在，则重复提交；
        2、或者在session设置token，提交后就销毁，每次提交订单时，检查是否有token。
        3、在redis中设置计时器，并设置过期时间。
        4、使用ajax局部刷新

        这里简单起见，只判断订单号不存在或者未付款时，才保存订单
         */
        User user = (User) session.getAttribute("user");
        ModelAndView mv = new ModelAndView();

        QueryWrapper queryWrapper = new QueryWrapper();
        String serialnumber = order.getSerialnumber();
        queryWrapper.eq("serialnumber", serialnumber);
        Orders one = orderService.getOne(queryWrapper);
        if (one == null || one.getStatus() == 0) {
            orderService.save(order, user);  //保存订单

            //生成订单以后，购物车数据要从数据库删除,清空购物车
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", user.getId());
            cartService.remove(queryWrapper); //清空购物车

            mv.addObject("cartList", new ArrayList<CartVO>());
            mv.addObject("order", order);
            mv.setViewName("settlement3");
        } else {
            mv.setViewName("/error/resubmit");
        }
        return mv;

    }
}

