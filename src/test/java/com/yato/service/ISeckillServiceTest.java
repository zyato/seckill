package com.yato.service;

import com.yato.dto.Exposer;
import com.yato.dto.SeckillExcution;
import com.yato.exception.RepeatKillException;
import com.yato.exception.SeckillClosedException;
import com.yato.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring/spring-*.xml") 效果一样
@ContextConfiguration({"classpath:spring/spring-mapper.xml",
                        "classpath:spring/spring-service.xml"})
public class ISeckillServiceTest {
    Logger logger = LoggerFactory.getLogger("classpath:logback.xml");
    @Autowired
    ISeckillService service;
    @Test
    public void getSeckillList() {
//        service.getSeckillList().forEach(System.out::println);
        logger.info("list = {}", service.getSeckillList());
    }

    @Test
    public void getById() {
        System.out.println(service.getById(1000L));
    }

    @Test
    public void exportSeckillUrl() {
        System.out.println(service.exportSeckillUrl(1001L));
    }
    @Test
    public void excuteSeckill() {
        System.out.println(service.excuteSeckill(1001L, 8570751150L, "bd5c7701f9d40c0461cf74d123cb9c3d"));
    }

    @Test
    public void seckillLogin() {
        Long id = 1002L;
        Long phone = 110L;
        Exposer exposer = service.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}", exposer);
            try {
                SeckillExcution ex = service.excuteSeckill(id, phone, exposer.getMd5());
                logger.info("excution={}", ex);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillClosedException e) {
                logger.error(e.getMessage());
            }
        }
        else{
            logger.warn("exposer={}", exposer);
        }
    }
}