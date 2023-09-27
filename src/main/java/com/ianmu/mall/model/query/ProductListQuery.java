package com.ianmu.mall.model.query;

import lombok.Data;

import java.util.List;

/**
 * ProductListQuery 查询商品列表的Query
 *
 * @author darre
 * @version 2023/09/27 22:24
 **/
@Data
public class ProductListQuery {

    private String keyword;

    private List<Integer> categoryIds;
}
