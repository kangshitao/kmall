package com.kang.kmall.mapper;

import com.kang.kmall.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface ProductMapper extends BaseMapper<Product> {

    //根据id扣除库存
    int updateSale(Product product);
}
