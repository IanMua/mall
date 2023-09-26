package com.ianmu.mall.common;

import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Assert 断言
 *
 * @author darre
 * @version 2023/09/17 12:17
 **/
public class Assert {

    public static void isNull(Object object, MallExceptionEnum exceptionEnum) {
        if (object == null) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isNotNull(Object object, MallExceptionEnum exceptionEnum) {
        if (!(object == null)) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isEmpty(Object object, MallExceptionEnum exceptionEnum) {
        if (ObjectUtils.isEmpty(object)) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isNotEmpty(Object object, MallExceptionEnum exceptionEnum) {
        if (!ObjectUtils.isEmpty(object)) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isZero(Object object, MallExceptionEnum exceptionEnum) {
        if (ObjectUtils.isEmpty(object) || Objects.equals(object.toString(), "0")) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isTrue(Object object, MallExceptionEnum exceptionEnum) {
        if (ObjectUtils.isEmpty(object) || Objects.equals(object.toString(), "true")) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }

    public static void isFalse(Object object, MallExceptionEnum exceptionEnum) {
        if (ObjectUtils.isEmpty(object) || Objects.equals(object.toString(), "false")) {
            throw new MallException(exceptionEnum.getCode(), exceptionEnum.getMsg());
        }
    }
}
