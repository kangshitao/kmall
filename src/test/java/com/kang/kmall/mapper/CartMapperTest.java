package com.kang.kmall.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年7月31日 下午5:49
 */
@SpringBootTest
class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;
    @Test
    void cartMapperTest(){
        cartMapper.selectList(null).forEach(System.out::println);
    }
}