package com.kang.kmall.service;

import com.kang.kmall.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.kmall.viewObject.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllCartByUserId(Integer id);
}
