package com.yato.exception;

/**
 * 重复秒杀异常（运行期异常）
 * spring 声明式事务，如果遇到的是非运行期异常，不会回滚操作，所以为了让事务回滚，继承运行时异常
 * @author yato
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatKillException(String message) {
        super(message);
    }
}
