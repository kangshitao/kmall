package com.kang.kmall.mapper;

import com.kang.kmall.entity.MsOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kangshitao
 * @date 2021年8月8日 上午12:51
 */
@SpringBootTest
class MsOrderMapperTest {
    @Autowired
    private MsOrderMapper msOrderMapper;
    @Test
    void insertTest(){
        MsOrder msOrder = new MsOrder();
        msOrder.setSid(733);
        msOrder.setName("test");
        msOrder.setCreateTime(new Date());

        msOrderMapper.insert(msOrder);
    }
}