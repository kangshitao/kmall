package com.kang.service;

/**
 * @author Kangshitao
 * @date 2021年8月4日 下午12:09
 */
public interface OrderService {
    /**
     * 处理秒杀的下单方法
     * @param id 要秒杀的商品id
     * @return 返回订单id
     */
    public int kill(Integer id);

    //秒杀方法2，加入了md5
    int kill(Integer id, Integer userid, String md5);

    /**
     * 根据商品id和业务id生成md5值
     * @param id
     * @param userid
     * @return
     */
    String getMd5(Integer id, Integer userid);

}
