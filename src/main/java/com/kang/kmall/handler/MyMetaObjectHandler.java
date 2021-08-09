package com.kang.kmall.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Kangshitao
 * @date 2021年8月1日 下午3:05
 */

/**
 * MetaObjectHandler的实现类，用于自动填充update_time和create_time属性
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作的时候要自动赋的值
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //给metaObjec的createTime赋一个当前的系统时间
        //这里的metaObject就是要当前操作的user对象
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 执行更新操作的时候要自动赋的值
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
