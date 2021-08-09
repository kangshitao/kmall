package com.kang.service.impl;

import com.kang.dao.OrderDao;
import com.kang.dao.StockDao;
import com.kang.dao.UserDao;
import com.kang.entity.Order;
import com.kang.entity.Stock;
import com.kang.entity.User;
import com.kang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Kangshitao
 * @date 2021年8月4日 下午12:28
 */
@Service
@Transactional

public class OrderServiceImpl implements OrderService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 处理秒杀的下单方法
     *
     * @param id 要秒杀的商品id
     * @return 返回生成的订单id
     */
    /*方式1、synchronized悲观锁
     @Override
    public synchronized int kill(Integer id)｛｝
    方式2，将锁加到控制层调用此方法处。
     */
    @Override
    public int kill(Integer id) {
        //0.检验redis中秒杀商品是否超时
        //redis中，key为kill+id，value是商品id
        if(!stringRedisTemplate.hasKey("kill" + id)){
            throw new RuntimeException("活动不在时间允许范围内");
        }


        //1.校验库存
        Stock stock = checkStock(id);
        //2.扣除库存
        updateSale(stock);
        //3.创建订单
        return createOrder(stock);
    }

    @Override
    public int kill(Integer id, Integer userid, String md5) {
        //0.检验redis中秒杀商品是否超时
        //redis中，key为kill+id，value是商品id
        if(!stringRedisTemplate.hasKey("kill" + id)){
            throw new RuntimeException("活动不在时间允许范围内");
        }

        //验证md5签名
        String hashkey = "KEY_"+userid+"_"+id;
        String s = stringRedisTemplate.opsForValue().get(hashkey);
        if(s==null) throw new RuntimeException("签名验证失败，请求非法");
        if (!s.equals(md5)) {
            throw new RuntimeException("当前请求数据不合法，请稍后再试");
        }


        //1.校验库存
        Stock stock = checkStock(id);
        //2.扣除库存
        updateSale(stock);
        //3.创建订单
        return createOrder(stock);
    }

    //根据商品id和用户id生成md5值,并放入Redis缓存中，设置有效时间
    @Override
    public String getMd5(Integer id, Integer userid) {
        //验证用户id合法性
        User user = userDao.findById(userid);
        if(user==null) throw new RuntimeException("用户信息不存在");

        //验证商品id合法性
        Stock stock = stockDao.checkStock(id);
        if(stock==null) throw new RuntimeException("商品信息不合法");

        //生成hashkey
        String hashkey = "KEY_"+userid+"_"+id;
        //生成md5，这里的!Q*js#是盐，应该是随机生成的
        String key = DigestUtils.md5DigestAsHex((userid+id+"!Q*jS#").getBytes());
        //写入redis，并设置md5的存活时间
        stringRedisTemplate.opsForValue().set(hashkey,key,120, TimeUnit.SECONDS);
        return key;
    }

    //根据商品id校验库存（根据已售和库存判断）
    private Stock checkStock(Integer id) {
        Stock stock = stockDao.checkStock(id);
        if (stock.getSale().equals(stock.getCount())) {
            throw new RuntimeException("库存不足");
        } else {
            return stock;
        }
    }

    //扣除库存
    private void updateSale(Stock stock) {
//        stock.setSale(stock.getSale() + 1);
        //在sql层面完成销量+1和版本号+1的操作，修改mapper配置文件即可
        //根据数据库行锁的性质，对某一行修改的时候，会阻塞其他的sql语句对当前行修改。
        //因此，sql层面上的更改是安全的
        int updateRows = stockDao.updateSale(stock);
        if(updateRows==0){ //如果更新行数为0，说明更新失败，即抢购失败
            throw new RuntimeException("抢购失败，请重试");
        }
    }

    //创建订单
    private Integer createOrder(Stock stock) {
        Order order = new Order();
        order.setSid(stock.getId());
        order.setName(stock.getName());
        order.setCreateTime(new Date());
        orderDao.createOrder(order);
        return order.getId();
    }
}
