package com.kang.kmall.mapper;

import com.kang.kmall.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午11:58
 */
@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void updateSaleTest(){
        Product product = productMapper.selectById(733);
        System.out.println(product);
        int i = productMapper.updateSale(product);
        System.out.println("更新结果行数："+i);
    }
}