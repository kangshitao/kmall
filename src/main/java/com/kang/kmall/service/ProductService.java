package com.kang.kmall.service;

import com.kang.kmall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface ProductService extends IService<Product> {
    List<Product> findByCategotyId(String level,Integer categoryId);
}
