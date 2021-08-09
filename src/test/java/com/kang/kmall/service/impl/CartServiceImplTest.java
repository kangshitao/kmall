package com.kang.kmall.service.impl;

import com.kang.kmall.service.CartService;
import com.kang.kmall.viewObject.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年8月2日 下午12:04
 */
@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    @Test
    void findAllCartByUserIdTest(){
        List<CartVO> allCartByUserId = cartService.findAllCartByUserId(44);
        allCartByUserId.forEach(System.out::println);
    }
}