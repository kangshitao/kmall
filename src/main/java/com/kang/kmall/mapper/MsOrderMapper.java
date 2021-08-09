package com.kang.kmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kang.kmall.entity.MsOrder;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午10:45
 */
public interface MsOrderMapper extends BaseMapper<MsOrder> {
    //创建订单
    void createOrder(MsOrder msOrder);
}
