package com.ianmu.mall.service.impl;

import com.ianmu.mall.common.Assert;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.dao.ProductMapper;
import com.ianmu.mall.model.pojo.Product;
import com.ianmu.mall.model.request.AddProductReq;
import com.ianmu.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductServiceImpl 商品服务实现类
 *
 * @author darre
 * @version 2023/09/24 21:20
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(AddProductReq req) {
        Product product = new Product();
        BeanUtils.copyProperties(req, product);

        Product queriedProduct = productMapper.selectByName(product.getName());
        Assert.isNotNull(queriedProduct, MallExceptionEnum.PRODUCT_NAME_EXISTED);

        int count = productMapper.insertSelective(product);
        Assert.isZero(count, MallExceptionEnum.INSERT_FAILED);
    }

    @Override
    public void update(Product updateProduct) {
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        Assert.isZero(count, MallExceptionEnum.PRODUCT_NOT_EXISTED);
    }

    @Override
    public void delete(Integer id) {
        int count = productMapper.deleteByPrimaryKey(id);
        Assert.isZero(count, MallExceptionEnum.PRODUCT_NOT_EXISTED);
    }
}
