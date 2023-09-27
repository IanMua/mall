package com.ianmu.mall.exception;

import lombok.Getter;

/**
 * ImoocMallExceptionEnum 异常枚举
 *
 * @author darre
 * @version 2023/09/17 11:45
 **/
@Getter
public enum MallExceptionEnum {
    // 业务异常

    // 登录异常
    NEED_USER_NAME(10001, "用户名不能为空"),
    NEED_PASSWORD(10002, "密码不能为空"),
    PASSWORD_TOO_SHORT(10003, "密码长度不能小于8位"),
    NAME_EXISTED(10004, "用户名已存在"),
    USERNAME_OR_PASSWORD_MISTAKEN(10005, "用户名或密码错误"),
    NEED_LOGIN(10006, "用户未登录"),
    NEED_ADMIN(10007, "无管理员权限"),

    // 目录异常
    CATEGORY_NAME_NOT_NULL(11001, "目录名称不能为空"),
    CATEGORY_NAME_EXISTED(11002, "目录名称已存在"),
    CATEGORY_NOT_EXISTED(11003, "目录不存在"),

    // 商品异常
    PRODUCT_NAME_EXISTED(12001, "商品名称已存在"),
    PRODUCT_PHOTO_UPLOAD_FAILED(12002, "商品图片上传失败"),
    PRODUCT_NOT_EXISTED(12001, "商品不存在"),

    // 请求异常
    REQUEST_NOT_NULL(18001, "请求参数不能为空"),
    REQUEST_PARAM_ERROR(18002, "参数错误"),

    // 读写操作相关异常
    INSERT_FAILED(19001, "添加失败"),
    UPDATE_FAILED(19002, "更新失败"),
    DELETE_FAILED(19003, "删除失败"),
    MKDIR_FAILED(19004, "文件夹创建失败"),

    // 系统异常
    SYSTEM_ERROR(20000, "系统异常"),
    ;

    /**
     * 异常码
     */
    private final Integer code;

    /**
     * 异常信息
     */
    private final String msg;

    MallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
