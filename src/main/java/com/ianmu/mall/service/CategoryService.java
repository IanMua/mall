package com.ianmu.mall.service;

import com.github.pagehelper.PageInfo;
import com.ianmu.mall.model.pojo.Category;
import com.ianmu.mall.model.request.AddCategoryReq;
import com.ianmu.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * CategoryService 分类目录
 *
 * @author darre
 * @version 2023/09/19 21:29
 **/
public interface CategoryService {
    void add(AddCategoryReq req);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo<Object> listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer();
}
