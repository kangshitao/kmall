package com.kang.service.impl;

import com.kang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author Kangshitao
 * @date 2021年8月6日 上午11:36
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public int saveUserCount(Integer userId) {
        //根据用户id，生成key
        String limitKey = "LIMIT" + "_" + userId;
        String limitNum = stringRedisTemplate.opsForValue().get(limitKey);
        int limit = -1;
        if(limitNum==null){//如果是第一次调用,则向Redis中添加键值对，并设置存活时间
            //设置存活时间的目的是，只限制用户在每段时间内的访问次数
            //限制60s内只能访问10次，每次访问都会重置时间，受限后，只有冷却60s后才能访问
            stringRedisTemplate.opsForValue().set(limitKey,"0",60, TimeUnit.SECONDS);
        }else{
            limit = Integer.parseInt(limitNum) + 1;
            stringRedisTemplate.opsForValue().set(limitKey,String.valueOf(limit),60,TimeUnit.SECONDS);
        }
        return limit;
    }

    @Override
    public boolean isExcessLimit(Integer userId) {
        String limitKey = "LIMIT" + "_" + userId;
        String limitNum = stringRedisTemplate.opsForValue().get(limitKey);
        if(limitNum==null){
            System.out.println("该用户没有访问次数记录，疑似异常");
            return true;
        }
        //在指定时间内，单个用户最多访问10次
        return Integer.parseInt(limitNum)>=10; //大于等于10或者没有记录值的时候，返回true，拒绝请求
    }
}
