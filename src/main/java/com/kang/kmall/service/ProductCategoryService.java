package com.kang.kmall.service;

import com.kang.kmall.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.kmall.viewObject.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getProductCategoryVO();
}
