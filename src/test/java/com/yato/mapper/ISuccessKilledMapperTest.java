package com.yato.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-mapper.xml")
public class ISuccessKilledMapperTest {

    @Autowired
    ISuccessKilledMapper mapper;
    @Test
    public void insertSuccessKilledInfo() {
        System.out.println(mapper.insertSuccessKilledInfo(1000L, 119L));
    }

    @Test
    public void queryBySeckillIdWithSeckill() {
        System.out.println(mapper.queryBySeckillIdWithSeckill(1000L, 111L));
    }
}