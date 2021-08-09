package com.kang.kmall.service;

import com.kang.kmall.entity.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author Kangshitao
 * @date 2021年8月3日 上午11:29
 */
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Test
    void saveTest(){
        Orders order = new Orders();
        order.setUserId(44);
        order.setLoginName("kang");
        order.setUserAddress("adredsssss");
        order.setUpdateTime(LocalDateTime.now());
        orderService.save(order);
    }
}