package com.yato.exception;

/**
 * 秒杀相关业务异常
 * @author 86185
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeckillException(String message) {
        super(message);
    }
}
