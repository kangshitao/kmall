package com.kang.kmall.service;

import com.kang.kmall.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kangshitao
 * @since 2021-07-31
 */
public interface UserService extends IService<User> {
    //向redis中写入用户访问次数
    int saveUserCount(Integer userId);

    //判断当前用户的访问次数是否超过限制
    boolean isExcessLimit(Integer userId);

}
