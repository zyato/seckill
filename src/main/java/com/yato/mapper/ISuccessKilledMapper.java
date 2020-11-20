package com.yato.mapper;

import com.yato.entity.Seckill;
import com.yato.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;

/**
 * 秒杀成功明细表的持久层接口
 * @author yato
 */
public interface ISuccessKilledMapper {
    /**
     * 插入购买明细信息，可以过滤同一用户重复下单（创建表时，使用了seckill_id 和 userPhone 作为联合主键 ）
     * @param seckillId
     * @param userPhone
     * @return 返回影像数据库的行数
     */
    int insertSuccessKilledInfo(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);

    /**
     * 根据 id 查询 successKilled记录并携带秒杀产品的对象实体
     * @param seckillId
     * @return 返回秒杀成功信息的实例，携带了被秒杀的产品
     */
    SuccessKilled queryBySeckillIdWithSeckill(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);

}
