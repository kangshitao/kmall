package com.kang.kmall.service;

import com.kang.kmall.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kang.kmall.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface OrderService extends IService<Orders> {
    public boolean save(Orders entity, User user);
}
