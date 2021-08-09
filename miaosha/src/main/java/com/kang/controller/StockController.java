package com.kang.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.kang.service.OrderService;
import com.kang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Kangshitao
 * @date 2021年8月4日 下午12:04
 */
@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    //创建令牌桶实例
    private RateLimiter rateLimiter = RateLimiter.create(10); //每秒在令牌桶中生成20个令牌，即放行20个请求。

    //测试令牌桶算法
    @GetMapping("/sale")
    public String sale(Integer id){
        //方式1、直接获取，没有获取到token的请求会一直阻塞，直到获取到token
        //System.out.println("等待的时间："+rateLimiter.acquire());

        //方式2、设置一个等待时间，如果等待时间内获取到了token令牌，则处理业务，否则，抛弃请求。
        if(!rateLimiter.tryAcquire(2, TimeUnit.SECONDS)){//设置超时时间为5秒
            System.out.println("当前请求被限流，被抛弃，无法调用后续秒杀任务");
            return "抢购失败";
        }

        System.out.println("拿到令牌，开始处理业务-------------");
        return "抢购成功";
    }

    //秒杀方法1，仅使用乐观锁防止超卖
    @GetMapping("/kill")
    public String kill(Integer id) {
        System.out.println("秒杀商品的id：" + id);

        //根据秒杀商品的id，调用秒杀业务
        try {  //对于库存不足抛出的异常，进行处理
            //第二种方式。将同步锁加到service层的事务外面
//            synchronized (this){
//                int orderId = orderService.kill(id);
//                return "秒杀成功，订单id:"+orderId;
//            }
            int orderId = orderService.kill(id);
            return "秒杀成功，订单id:" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //秒杀方法2,使用乐观锁防止超卖，使用令牌桶算法限流
    @GetMapping("/killtoken")
    public String killtoken(Integer id) {
        /*
        这种方式的弊端是，当请求的等待时间超时后，请求被抛弃，可能会导致商品不会被抢购完，因为大多数请求都被限流抛弃了
         */
        System.out.println("正在抢购商品：" + id);

        //加入令牌桶限流
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)) { //如果请求的等待时间超时，则请求被抛弃，限流
            System.out.println("抛弃请求，抢购失败，活动过于火爆，请重试！");
            return "抢购失败，活动过于火爆，请重试！";
        }


        //根据秒杀商品的id，调用秒杀业务
        try {
            //使用乐观锁防止超卖，在mapper配置文件中根据版本号实现乐观锁机制
            int orderId = orderService.kill(id);
            return "秒杀成功，订单id:" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //秒杀方法3,乐观锁防止超卖，令牌桶算法限流，md5校验实现接口隐藏
    @GetMapping("/killtokenmd5")
    public String killtokenmd5(Integer id,Integer userid,String md5) {
        /*
        这种方式的弊端是，当请求的等待时间超时后，请求被抛弃，可能会导致商品不会被抢购完，因为大多数请求都被限流抛弃了
         */
        System.out.println("正在抢购商品：" + id);

        //加入令牌桶限流
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)) { //如果请求的等待时间超时，则请求被抛弃，限流
            System.out.println("抛弃请求，抢购失败，活动过于火爆，请重试！");
            return "抢购失败，活动过于火爆，请重试！";
        }

        //根据秒杀商品的id，调用秒杀业务
        try {
            //使用乐观锁防止超卖，md5校验隐藏接口
            int orderId = orderService.kill(id,userid,md5);
            return "秒杀成功，订单id:" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //秒杀方法4,乐观锁防止超卖，令牌桶算法限流，md5校验实现接口隐藏，单用户访问频率限制
    @GetMapping("/killtokenmd5limit")
    public String killtokenmd5limit(Integer id,Integer userid,String md5) {
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
            int orderId = orderService.kill(id,userid,md5);
            return "秒杀成功，订单id:" + orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //获取md5值的方法
    @RequestMapping("/md5")
    public String getMd5(Integer id, Integer userid){
        String md5;
        try{
            md5 = orderService.getMd5(id,userid);
        }catch (Exception e){
            e.printStackTrace();
            return "获取md5失败:"+e.getMessage();
        }
        return "获取md5信息为:"+md5;
    }
}
