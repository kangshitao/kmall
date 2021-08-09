package com.kang.kmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年8月1日 下午5:21
 */
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;
    @Test
    void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevelone_id",548);
        List<Product> products = productService.listByMap(map);
        products.forEach(System.out::println);
    }
}