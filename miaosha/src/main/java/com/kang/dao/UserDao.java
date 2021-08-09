package com.kang.dao;

import com.kang.entity.User;

/**
 * @author Kangshitao
 * @date 2021年8月6日 上午12:18
 */
public interface UserDao {
    public User findById(Integer userid);
}
