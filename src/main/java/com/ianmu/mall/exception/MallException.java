package com.ianmu.mall.exception;

import lombok.Getter;

/**
 * ImoocMallException 统一异常
 *
 * @author darre
 * @version 2023/09/17 12:12
 **/
@Getter
public class MallException extends RuntimeException {
    private final Integer code;
    private final String message;

    public MallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MallException(MallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }
}
