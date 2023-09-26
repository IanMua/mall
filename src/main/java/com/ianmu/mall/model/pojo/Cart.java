package com.ianmu.mall.model.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Cart implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private Date createTime;

    private Date updateTime;
}