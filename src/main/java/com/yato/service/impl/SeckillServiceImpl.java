package com.yato.service.impl;

import com.yato.dto.Exposer;
import com.yato.dto.SeckillExcution;
import com.yato.entity.Seckill;
import com.yato.entity.SuccessKilled;
import com.yato.enums.SeckillStateEnum;
import com.yato.exception.RepeatKillException;
import com.yato.exception.SeckillClosedException;
import com.yato.exception.SeckillException;
import com.yato.mapper.ISeckillMapper;
import com.yato.mapper.ISuccessKilledMapper;
import com.yato.service.ISeckillService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service("seckillService")
public class SeckillServiceImpl implements ISeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISeckillMapper seckillMapper;
    @Autowired
    private ISuccessKilledMapper successKilledMapper;
    // 加入一个用于混淆 md5 码的字符串
    private final String slat = "asdfhalfhah2@#￥……TWER><:<>M349097f$@#$QＲＱＷＲＣＱＷ";
    @Override
    public List<Seckill> getSeckillList() {
        return seckillMapper.queryAll(0, 4);
    }

    @Override
    public Seckill getById(Long seckillId) {
        return seckillMapper.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = getById(seckillId);
        if (null == seckill){
            return new Exposer(false, seckillId);
        }
        Date start = seckill.getStartTime();
        Date end = seckill.getEndTime();
        Date now = new Date();
        if (now.getTime() <  start.getTime() || now.getTime() > end.getTime()){
            return new Exposer(false, seckillId, now.getTime(),
                    start.getTime(), end.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(Long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点
     * 1. 开发团队达成一致约定，明确标注事务方法的编程风格。
     * 2. 保证事务方法的执行时间尽可能端，不要穿插其他网络操作 RPC/HTTP 请求等（网络操作执行时间相对较长），或者剥离到事务方法外部。
     * 3. 不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     */
    @Override
    @Transactional
    public SeckillExcution excuteSeckill(Long seckillId, Long userPhone, String md5) throws SeckillClosedException, SeckillException, RepeatKillException {
        if (null == md5 || !md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        // 执行秒杀逻辑：减库存 + 记录购买行为
        Date now = new Date();
        try { // 如果产生了其他的异常，需要记录到日志
            int changedRows = seckillMapper.reduceNumber(seckillId, now);
            if (changedRows <= 0){
                // 没有更新到记录，秒杀结束
                throw new SeckillClosedException("seckill is closed");
            }
            else{
                // 记录购买行为
                int insertCount = successKilledMapper.insertSuccessKilledInfo(seckillId, userPhone);
                // 因为 商品 id 和用户手机号作为联合主键，只能插入一次，如果插入多次，由于持久层的插入的
                // sql 语句使用的是 insert ignore into，不会产生异常，但是返回值是0（影响的行数）
                if (insertCount <= 0){
                    // 重复秒杀
                    throw new RepeatKillException("seckill repeated");
                }
                else{
                    // 秒杀成功
                    SuccessKilled successKilled = successKilledMapper.queryBySeckillIdWithSeckill(seckillId, userPhone);
                    return new SeckillExcution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        }
        // 预先捕获预定义的异常
        catch (RepeatKillException e){
            throw e;
        }catch (SeckillClosedException e){
            throw e;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译期异常，转化为运行期异常，防止 spring 遇到非运行时异常不回滚，而
            // 造成减库存和添加购买行为这一事务不再具有原子性（保证产生异常一定能够回滚）
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
    }
}
