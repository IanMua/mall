package com.ianmu.mall.service;

import com.ianmu.mall.model.pojo.Product;
import com.ianmu.mall.model.request.AddProductReq;

/**
 * ProductService 商品
 *
 * @author darre
 * @version 2023/9/24 21:19
 **/
public interface ProductService {
    void add(AddProductReq req);

    void update(Product updateProduct);

    void delete(Integer id);
}
