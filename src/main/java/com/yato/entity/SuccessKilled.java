package com.yato.entity;

import java.util.Date;

/**
 * 秒杀成功明细表对应的实体类
 * @author yato
 */
public class SuccessKilled {
    private Long seckillId;
    private Long userPhone;
    private Short state;
    private Date createTime;
    /**
     * 一种秒杀商品（同一 id，库存不止一个）可以对应多个秒杀成功的记录
     * 所以是多对一，应该在 “一” 的实体类中加入 “多” 的实例。
     */
    private Seckill seckill;

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
