package com.ianmu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.common.Constant;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.dao.ProductMapper;
import com.ianmu.mall.model.pojo.Product;
import com.ianmu.mall.model.query.ProductListQuery;
import com.ianmu.mall.model.request.AddProductReq;
import com.ianmu.mall.model.request.ProductListReq;
import com.ianmu.mall.model.vo.CategoryVO;
import com.ianmu.mall.service.CategoryService;
import com.ianmu.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    CategoryService categoryService;

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

    @Override
    public void batchUpdateSellStatus(ArrayList<Integer> ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);
    }

    @Override
    public PageInfo<Product> listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectListForAdmin();
        return new PageInfo<>(productList);
    }

    @Override
    public Product detail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Product> list(ProductListReq req) {
        ProductListQuery productListQuery = new ProductListQuery();

        // 搜索处理
        if (!ObjectUtils.isEmpty(req.getKeyword())) {
            String keyword = new StringBuilder()
                    .append("%")
                    .append(req.getKeyword())
                    .append("%")
                    .toString();
            productListQuery.setKeyword(keyword);
        }

        // 目录处理：如果查某个目录下的商品，还需要查询子目录，所以要获取目录id的List
        if (!ObjectUtils.isEmpty(req.getCategoryId())) {
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(req.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(req.getCategoryId());
            getCategoryIds(categoryVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        // 排序处理
        String orderBy = req.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
            PageHelper.startPage(req.getPageNum(), req.getPageSize(), orderBy);
        } else {
            PageHelper.startPage(req.getPageNum(), req.getPageSize());
        }

        return new PageInfo<>(productMapper.selectList(productListQuery));
    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        for (CategoryVO item : categoryVOList) {
            if (!ObjectUtils.isEmpty(item)) {
                categoryIds.add(item.getId());
                getCategoryIds(item.getChildCategory(), categoryIds);
            }
        }
    }
}
