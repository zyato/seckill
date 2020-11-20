package com.yato.mapper;

import com.yato.entity.Seckill;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mapper.xml"})
public class ISeckillMapperTest {
    @Autowired
    ISeckillMapper mapper;
    @Test
    public void reduceNumber() {
//        System.out.println(mapper);
        System.out.println(mapper.reduceNumber(1000L, new Date()));
    }

    @Test
    public void queryById() {
        System.out.println("begin");
        System.out.println("-------------" + mapper.queryById(1000L));
        System.out.println("end");
    }

    @Test
    public void queryAll() {
        for (Seckill seckill : mapper.queryAll(0, 5)) {
            System.out.println(seckill);
        }
    }
}