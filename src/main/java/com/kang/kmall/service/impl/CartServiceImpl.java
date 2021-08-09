package com.kang.kmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Cart;
import com.kang.kmall.entity.Product;
import com.kang.kmall.enums.ResultEnum;
import com.kang.kmall.exception.KMallException;
import com.kang.kmall.mapper.CartMapper;
import com.kang.kmall.mapper.ProductMapper;
import com.kang.kmall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kang.kmall.viewObject.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 重写save方法，在添加购物车的时候判断库存
     * @param entity
     * @return
     */
    @Override
    public boolean save(Cart entity) {
        Product product = productMapper.selectById(entity.getProductId());
        int newStock = product.getStock() - entity.getQuantity();
        if(newStock<0){ //库存不足
            throw new KMallException(ResultEnum.STOCK_ERROR);
        }

        product.setStock(newStock);
        productMapper.updateById(product);//如果库存足够，则更新库存
        if(cartMapper.insert(entity)==1){
            return true;
        }
        return false;
    }

    @Override
    public List<CartVO> findAllCartByUserId(Integer id) {
        List<CartVO> cartVOList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",id);
        //根据用户id找到其购物车的所有行
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        for (Cart cart : cartList) {
            //对每个购物车类，根据商品id找到商品信息，然后创建CartVO对象
            CartVO cartVO = new CartVO();
            Product product = productMapper.selectById(cart.getProductId());
            //product和cart都有id这个属性，重复赋值，后面的会覆盖前面的值
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);  //属性填充，最终cartOV对象的id等于cart的id
            cartVO.setProductId(product.getId());
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
