package com.kang.kmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Product;
import com.kang.kmall.mapper.ProductMapper;
import com.kang.kmall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findByCategotyId(String level, Integer categoryId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("categorylevel"+level+"_id",categoryId);
        return productMapper.selectList(wrapper);
    }
}
