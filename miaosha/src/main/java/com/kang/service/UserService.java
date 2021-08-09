package com.kang.service;

/**
 * @author Kangshitao
 * @date 2021年8月6日 上午11:35
 */
public interface UserService {
    //向redis中写入用户访问次数
    int saveUserCount(Integer userId);

    //判断当前用户的访问次数是否超过限制
    boolean isExcessLimit(Integer userId);
}
