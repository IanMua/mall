package com.ianmu.mall.controller;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController 前台商品
 *
 * @author darre
 * @version 2023/09/27 22:05
 **/
@Tag(name = "前台商品", description = "前台商品接口")
@RestController
@RequestMapping("product")
@Validated
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(summary = "商品详情")
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ApiRestResponse<Object> detail(@Valid @Parameter(name = "id", description = "商品ID") @NotNull(message = "商品ID不能为空") Integer id) {
        return ApiRestResponse.success(productService.detail(id));
    }
}
