package com.yato.exception;

/**
 * 秒杀已关闭异常
 * @author yato
 */
public class SeckillClosedException extends SeckillException {
    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillClosedException(String message) {
        super(message);
    }
}
