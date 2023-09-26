package com.ianmu.mall.enums;

import lombok.Getter;

/**
 * RoleEnum 角色
 *
 * @author darre
 * @version 2023/09/17 20:47
 **/
@Getter
public enum RoleEnum {

    USER(1, "普通用户"),
    ADMIN(2, "管理员"),
    ;

    private final Integer code;
    private final String label;


    RoleEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
