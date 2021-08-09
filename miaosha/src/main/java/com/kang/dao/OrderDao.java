package com.kang.dao;

import com.kang.entity.Order;

/**
 * @author Kangshitao
 * @date 2021年8月4日 下午4:11
 */
public interface OrderDao {
    //创建订单
    void createOrder(Order order);
}
