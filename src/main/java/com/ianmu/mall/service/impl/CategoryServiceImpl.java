package com.ianmu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.dao.CategoryMapper;
import com.ianmu.mall.model.pojo.Category;
import com.ianmu.mall.model.request.AddCategoryReq;
import com.ianmu.mall.model.vo.CategoryVO;
import com.ianmu.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CategoryServiceImpl 分类目录
 *
 * @author darre
 * @version 2023/09/19 21:29
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 添加目录
     *
     * @param req 请求参数
     */
    @Override
    public void add(AddCategoryReq req) {
        // 属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(req, category);

        // 查询是否有同名目录
        Category queriedCategory = categoryMapper.selectByName(category.getName());
        Assert.isNotNull(queriedCategory, MallExceptionEnum.CATEGORY_NAME_EXISTED);

        // 判断添加是否成功
        int count = categoryMapper.insertSelective(category);
        Assert.isZero(count, MallExceptionEnum.INSERT_FAILED);
    }

    /**
     * 更新目录
     *
     * @param updateCategory 更新请求参数
     */
    @Override
    public void update(Category updateCategory) {
        // 查询是否有相同目录，如果查询到的目录不为空，且目录ID和请求参数的目录ID相同，判断为更新
        Category queriedCategory = categoryMapper.selectByPrimaryKey(updateCategory.getId());
        if (!ObjectUtils.isEmpty(queriedCategory) && Objects.equals(queriedCategory.getId(), updateCategory.getId())) {
            int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
            Assert.isZero(count, MallExceptionEnum.UPDATE_FAILED);
        }
        // 否则抛出异常
        else if (ObjectUtils.isEmpty(queriedCategory)) {
            throw new MallException(MallExceptionEnum.CATEGORY_NOT_EXISTED);
        } else {
            throw new MallException(MallExceptionEnum.CATEGORY_NAME_EXISTED);
        }
    }

    @Override
    public void delete(Integer id) {
        Category queriedCategory = categoryMapper.selectByPrimaryKey(id);
        Assert.isNull(queriedCategory, MallExceptionEnum.DELETE_FAILED);

        int count = categoryMapper.deleteByPrimaryKey(id);
        Assert.isZero(count, MallExceptionEnum.DELETE_FAILED);
    }

    @Override
    public PageInfo<Object> listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, "type");
        List<Category> categoryList = categoryMapper.selectList();
        return new PageInfo<>(categoryList);
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer() {
        List<CategoryVO> categoryVOArrayList = new ArrayList<>();
        recursivelyFineCategories(categoryVOArrayList, 0);

        return categoryVOArrayList;
    }

    private void recursivelyFineCategories(List<CategoryVO> categoryVOArrayList, Integer parentId) {
        // 递归获取所有子类别，并组合成为一个目录数
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!ObjectUtils.isEmpty(categoryList)) {
            for (Category category : categoryList) {
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOArrayList.add(categoryVO);
                recursivelyFineCategories(categoryVO.getChildCategory(), categoryVO.getId());
            }

        }
    }
}
