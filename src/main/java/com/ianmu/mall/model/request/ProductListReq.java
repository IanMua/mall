package com.ianmu.mall.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductListReq implements Serializable {

    private String keyword;

    private Integer categoryId;

    private String orderBy;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}