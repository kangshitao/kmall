package com.kang.kmall.service;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午10:37
 */
public interface MsOrderService {
    /**
     *
     * @param id 要秒杀的商品id
     * @param userid 用户id
     * @param md5 根据用户id和商品id生成的md5校验码
     * @return 返回订单id
     */
    //秒杀方法
    int kill(Integer id, Integer userid, String md5);

    /**
     * 根据商品id和业务id生成md5值
     * @param id
     * @param userid
     * @return
     */
    String getMd5(Integer id, Integer userid);
}
