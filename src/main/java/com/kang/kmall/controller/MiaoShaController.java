package com.kang.kmall.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.kang.kmall.entity.Product;
import com.kang.kmall.entity.User;
import com.kang.kmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午10:15
 */
@Controller
public class MiaoShaController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MsOrderService msOrderService;

    @Autowired
    private UserService userService;

    //创建令牌桶实例
    private RateLimiter rateLimiter = RateLimiter.create(10); //每秒在令牌桶中生成20个令牌，即放行20个请求。

    @GetMapping("/ms/{id}")
    public ModelAndView miaosha(@PathVariable("id")Integer id, HttpSession session){
        ModelAndView mv = new ModelAndView();
        Product product = productService.getById(id);
        mv.setViewName("miaosha");
        mv.addObject("product",product);
        mv.addObject("list",productCategoryService.getProductCategoryVO());
        User user = (User) session.getAttribute("user");
        mv.addObject("cartList",cartService.findAllCartByUserId(user.getId()));
        return mv;
    }

    //秒杀方法,乐观锁防止超卖，令牌桶算法限流，md5校验实现接口隐藏，单用户访问频率限制
    @GetMapping("/kill/{id}/{userid}/{md5}")
    @ResponseBody
    public String kill(@PathVariable("id")Integer id,
                       @PathVariable("userid")Integer userid,
                       @PathVariable("md5")String md5) {
        //加入令牌桶限流
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)) { //如果请求的等待时间超时，则请求被抛弃，限流
            System.out.println("抛弃请求，抢购失败，活动过于火爆，请重试！");
            return "抢购失败，活动过于火爆，请重试！";
        }

        //根据秒杀商品的id，调用秒杀业务
        try {
            //单用户调用接口频率限制
            int count = userService.saveUserCount(userid); //获取当前请求之前的访问次数
            System.out.println("截止至当前请求，用户已访问次数为："+count);
            if(userService.isExcessLimit(userid)){ //访问次数超过指定次数
                System.out.println("访问次数受限，请稍后再试");
                return "访问次数受限，请稍后再试";
            }

            //使用乐观锁防止超卖，md5校验隐藏接口
            int orderId = msOrderService.kill(id,userid,md5);
            return "秒杀成功，订单id:" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //获取md5值,并验证
    @GetMapping("/verify/{id}")
    public String getMd5(@PathVariable("id") Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        Integer userid = user.getId();
        String md5;
        try{
            md5 = msOrderService.getMd5(id,userid);
        }catch (Exception e){
            e.printStackTrace();
            String msg = "获取md5失败:"+e.getMessage();
            System.out.println(msg);
            return "forward:/msError/"+msg;
        }
        return "forward:/kill/"+id+"/"+userid+"/"+md5;
    }

    @GetMapping("/msError/{msg}")
    @ResponseBody
    private String msError(String msg){
        return msg;
    }
}
