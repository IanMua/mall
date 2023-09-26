package com.ianmu.mall.controller;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.model.pojo.Category;
import com.ianmu.mall.model.request.AddCategoryReq;
import com.ianmu.mall.model.request.UpdateCategoryReq;
import com.ianmu.mall.model.vo.CategoryVO;
import com.ianmu.mall.service.CategoryService;
import com.ianmu.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CategoryController 目录
 *
 * @author darre
 * @version 2023/09/19 21:09
 **/
@Tag(name = "目录", description = "目录管理")
@RestController
@RequestMapping("category")
@Valid
@Validated
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Operation(summary = "添加目录")
    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ApiRestResponse<Object> addCategory(HttpSession session, @Valid @RequestBody(description = "添加目录请求参数") AddCategoryReq req) {

        categoryService.add(req);

        return ApiRestResponse.success();
    }

    @Operation(summary = "更新目录")
    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public ApiRestResponse<Object> updateCategory(HttpSession session, @Valid @RequestBody(description = "更新目录请求参数") UpdateCategoryReq req) {

        Category category = new Category();
        BeanUtils.copyProperties(req, category);
        categoryService.update(category);

        return ApiRestResponse.success();
    }

    @Operation(summary = "删除目录")
    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public ApiRestResponse<Object> deleteCategory(@Parameter(name = "id", description = "目录ID", required = true) @RequestParam(name = "id") @NotNull(message = "目录ID不能为空") Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @Operation(summary = "分页查询目录列表", description = "需要管理员")
    @RequestMapping(value = "/admin/list", method = RequestMethod.GET)
    public ApiRestResponse<Object> listCategoryForAdmin(
            @Parameter(name = "pageNum", description = "页码", required = true) @NotNull(message = "页码不能为空") Integer pageNum,
            @Parameter(name = "pageSize", description = "每页条数", required = true) @NotNull(message = "每页条数不能为空") Integer pageSize) {

        return ApiRestResponse.success(categoryService.listForAdmin(pageNum, pageSize));
    }

    @Operation(summary = "前台目录列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiRestResponse<Object> listCategoryForAdmin() {
        List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer();

        return ApiRestResponse.success(categoryVOList);
    }
}
