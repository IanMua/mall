package com.ianmu.mall.exception;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.common.Assert;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalExceptionHandler 全局异常拦截处理
 *
 * @author darre
 * @version 2023/09/17 11:55
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ApiRestResponse<Object> handleException(Exception ex) {
        log.error("Default Exception: ", ex);
        return ApiRestResponse.error(MallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(MallException.class)
    public ApiRestResponse<Object> handleMallException(MallException ex) {
        log.error("MallException Exception: ", ex);
        return ApiRestResponse.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiRestResponse<Object> handleValidException(MethodArgumentNotValidException ex) {
        log.error("valid Exception: ", ex);

        // 把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (ex.hasErrors()) {
            ex.getAllErrors().forEach(item -> {
                String message = item.getDefaultMessage();
                list.add(message);
            });
        }

        Assert.isEmpty(list, MallExceptionEnum.REQUEST_PARAM_ERROR);

        return ApiRestResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString().substring(1, list.toString().length() - 1));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiRestResponse<Object> handleValidationException(ConstraintViolationException ex) {
        log.error("valid Exception: ", ex);

        List<String> exList = new ArrayList<>();
        String[] exArray = ex.getMessage().split(",");
        for (String item : exArray) {
            exList.add(item.split(":")[1].substring(1));
        }

        return ApiRestResponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), exList.toString().substring(1, exList.toString().length() - 1));
    }
}
