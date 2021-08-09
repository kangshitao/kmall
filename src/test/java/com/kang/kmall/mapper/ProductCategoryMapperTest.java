package com.kang.kmall.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年7月31日 下午6:10
 */
@SpringBootTest
class ProductCategoryMapperTest {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Test
    void test(){
        productCategoryMapper.selectList(null).forEach(System.out::println);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",2);
        queryWrapper.eq("parent_id",628);
        List list = productCategoryMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }
}