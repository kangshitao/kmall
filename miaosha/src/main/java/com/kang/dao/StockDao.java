package com.kang.dao;

import com.kang.entity.Stock;

/**
 * @author Kangshitao
 * @date 2021年8月4日 下午12:35
 */
public interface StockDao {
    //根据商品id查询库存
    Stock checkStock(Integer id);

    //根据id扣除库存
    int updateSale(Stock stock);
}
