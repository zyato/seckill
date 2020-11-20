package com.yato.service;

import com.yato.dto.Exposer;
import com.yato.dto.SeckillExcution;
import com.yato.entity.Seckill;
import com.yato.exception.RepeatKillException;
import com.yato.exception.SeckillClosedException;
import com.yato.exception.SeckillException;

import java.util.List;

/**
 * seckill 的业务层
 * 站在 “使用者” 的角度来设计接口
 * 分为三个方面：
 *      1. 方法定义粒度：必须明确接口的功能，而不必关注于接口怎么实现
 *          （比如，秒杀项目，肯定有一个执行秒杀的接口，只需要传入参数即可，而不必关注
 *          怎么减少库存、怎么添加用户购买行为等接口实现的问题）
 *      2. 参数：越简练、越直接越好
 *      3. 返回值类型：返回值一定要友好
 * @author yato
 */
public interface ISeckillService {
    /**
     * 查询所有秒杀商品列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据 id 查询一个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(Long seckillId);

    /**
     * 秒杀开启时，输出秒杀接口；
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     * @return 秒杀地址的dto
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExcution excuteSeckill(Long seckillId, Long userPhone, String md5) throws SeckillClosedException, SeckillException, RepeatKillException;
}
