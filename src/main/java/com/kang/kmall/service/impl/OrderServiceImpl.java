package com.kang.kmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kang.kmall.entity.Cart;
import com.kang.kmall.entity.OrderDetail;
import com.kang.kmall.entity.Orders;
import com.kang.kmall.entity.User;
import com.kang.kmall.mapper.CartMapper;
import com.kang.kmall.mapper.OrderDetailMapper;
import com.kang.kmall.mapper.OrderMapper;
import com.kang.kmall.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean save(Orders order, User user) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
        queryWrapper.eq("user_id", user.getId());
        List<Cart> carts = cartMapper.selectList(queryWrapper);

        if (carts != null) { //只有购物车不为空的时候，才会提交订单，防止浏览器回退导致的重复提交
            //存储order
            order.setUserId(user.getId());
            order.setLoginName(user.getLoginName());
            order.setStatus(1); //设置为已付款状态
            //直接调用Mapper中的方法。 自增主键的值会自动回填到order对象中。
            //另外，在注解或者配置文件中，使用useGeneratedKeys也能返回自增的主键
            orderMapper.insert(order);

            //存储order_detail
            for (Cart cart : carts) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductId(cart.getProductId());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setCost(cart.getCost());
                orderDetailMapper.insert(orderDetail);
            }
        }
        return true;
    }
}
