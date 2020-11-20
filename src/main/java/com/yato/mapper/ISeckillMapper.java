package com.yato.mapper;

import com.yato.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * seckill 的持久层接口
 * @author yato
 */
public interface ISeckillMapper {

    /**
     * 商品被秒杀后，减少库存
     * @param seckillId 商品 id
     * @param killTime 秒杀的时间
     * @return 返回影响数据库的行数，如果 等于 0，表明商品已售罄
     * 注意：java 没有保存形参的记录：
     *              例如：reduceNumber(Long seckillId， Date killTime)
     *                  会转变成 reduceNumber(arg0， arg1)
     *      如果有多个形参时， 使用 #{seckillId} 时 mybatis会提取不到对应参数的值
     *      所以在多个参数时要使用 mybatis 提供的 param 注解注明形参名
     */
    int reduceNumber(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据 id 查询对应秒杀的商品
     * @param seckillId  商品 id
     * @return 返回秒杀的商品
     */
    Seckill queryById(Long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return 返回一部分（一段区间）秒杀的商品
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
