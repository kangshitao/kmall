package com.kang.kmall.service.impl;

import com.kang.kmall.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年7月31日 下午6:26
 */
@SpringBootTest
class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryService service;
    @Test
    void test(){
        service.getProductCategoryVO();
    }
}