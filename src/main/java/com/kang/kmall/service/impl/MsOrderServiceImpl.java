package com.kang.kmall.service.impl;

import com.kang.kmall.entity.MsOrder;
import com.kang.kmall.entity.Product;
import com.kang.kmall.entity.User;
import com.kang.kmall.mapper.MsOrderMapper;
import com.kang.kmall.mapper.ProductMapper;
import com.kang.kmall.mapper.UserMapper;
import com.kang.kmall.service.MsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Kangshitao
 * @date 2021年8月7日 下午10:37
 */
@Service
@Transactional
public class MsOrderServiceImpl implements MsOrderService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MsOrderMapper msOrderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 处理秒杀的下单方法
     *
     * @param id 要秒杀的商品id
     * @return 返回生成的订单id
     */
    @Override
    public int kill(Integer id, Integer userid, String md5) {
        //0.检验redis中秒杀商品是否超时
        //redis中，key为kill+商品id，value是商品id
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
        Product product = checkStock(id);
        //2.扣除库存
        updateSale(product);
        //3.创建订单
        return createOrder(product);
    }

    //根据商品id和用户id生成md5值,并放入Redis缓存中，设置有效时间
    @Override
    public String getMd5(Integer id, Integer userid) {
        //验证用户id合法性
        User user = userMapper.selectById(userid);
        if(user==null) throw new RuntimeException("用户信息不存在");

        //验证商品id合法性
        Product product = productMapper.selectById(id);
        if(product==null) throw new RuntimeException("商品信息不合法");

        //生成hashkey
        String hashkey = "KEY_"+userid+"_"+id;
        //生成md5，这里的!Q*js#是盐，应该是随机生成的
        String key = DigestUtils.md5DigestAsHex((userid+id+"!Q*jS#").getBytes());
        //写入redis，并设置md5的存活时间
        stringRedisTemplate.opsForValue().set(hashkey,key,120, TimeUnit.SECONDS);
        return key;
    }

    //根据商品id校验库存（根据已售和库存判断）
    private Product checkStock(Integer id) {
        Product product = productMapper.selectById(id);
        if (product.getSale().equals(product.getStock())) {
            throw new RuntimeException("库存不足");
        } else {
            return product;
        }
    }

    //扣除库存
    private void updateSale(Product product) {
//        stock.setSale(stock.getSale() + 1);
        //在sql层面完成销量+1和版本号+1的操作，修改mapper配置文件即可
        //根据数据库行锁的性质，对某一行修改的时候，会阻塞其他的sql语句对当前行修改。
        //因此，sql层面上的更改是安全的
        int updateRows = productMapper.updateSale(product);
        if(updateRows==0){ //如果更新行数为0，说明更新失败，即抢购失败
            throw new RuntimeException("抢购失败，请重试");
        }
    }

    //创建订单
    private Integer createOrder(Product product) {
        MsOrder msOrder = new MsOrder();
        msOrder.setSid(product.getId());
        msOrder.setName(product.getName());
        msOrder.setCreateTime(new Date());
        //mybatis-plus自动增长主键默认是UUID，https://blog.csdn.net/qq_39084191/article/details/110236841
        //如果想要根据数据库自增数字自动生成，需要配置id-type
        msOrderMapper.insert(msOrder);
        return msOrder.getId();
    }
}
