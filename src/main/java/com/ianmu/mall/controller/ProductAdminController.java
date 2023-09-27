package com.ianmu.mall.controller;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.common.Constant;
import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.pojo.Product;
import com.ianmu.mall.model.request.AddProductReq;
import com.ianmu.mall.model.request.UpdateProductReq;
import com.ianmu.mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * ProductAdminController 后台商品管理
 *
 * @author darre
 * @version 2023/09/24 21:08
 **/
@Tag(name = "商品", description = "商品管理")
@RestController
@RequestMapping("product/admin")
@Validated
public class ProductAdminController {

    @Autowired
    ProductService productService;

    /**
     * 添加商品
     *
     * @param req 添加商品请求参数
     * @return 统一返回
     */
    @Operation(summary = "添加商品")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiRestResponse<Object> addProduct(@Valid @RequestBody(description = "添加商品请求参数") AddProductReq req) {
        // 商品添加
        productService.add(req);
        return ApiRestResponse.success();
    }

    @Operation(summary = "上传商品图片")
    @RequestMapping(value = "/upload/file", method = RequestMethod.POST)
    @SuppressWarnings("all")
    public ApiRestResponse<Object> upload(HttpServletRequest httpServletRequest, @Parameter(name = "file", description = "上传的商品图片") MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        Assert.isEmpty(fileName, MallExceptionEnum.PRODUCT_PHOTO_UPLOAD_FAILED);
        // 获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffixName;
        // 创建文件
        File fileDriectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        if (!fileDriectory.exists()) {
            if (!fileDriectory.mkdir()) {
                throw new MallException(MallExceptionEnum.MKDIR_FAILED);
            }
        }
        // 把入参文件写入到资产文件中
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new MallException(MallExceptionEnum.PRODUCT_PHOTO_UPLOAD_FAILED);
        }
        // 返回图片URI
        try {
            return ApiRestResponse.success(getHost(new URI(String.valueOf(httpServletRequest.getRequestURL()))) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            return ApiRestResponse.error(MallExceptionEnum.PRODUCT_PHOTO_UPLOAD_FAILED);
        }
    }

    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            effectiveURI = null;
        }
        return effectiveURI;
    }

    @Operation(summary = "更新商品")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ApiRestResponse<Object> update(@Valid @RequestBody(description = "更新商品请求参数") UpdateProductReq req) {

        Product product = new Product();
        BeanUtils.copyProperties(req, product);
        productService.update(product);

        return ApiRestResponse.success();
    }

    @Operation(summary = "删除商品")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ApiRestResponse<Object> delete(@NotNull(message = "商品ID不能为空") @Parameter(name = "id", description = "商品ID") Integer id) {

        productService.delete(id);

        return ApiRestResponse.success();
    }

    @Operation(summary = "批量更新上下架")
    @RequestMapping(value = "batchUpdateSellStatus", method = RequestMethod.POST)
    public ApiRestResponse<Object> batchUpdateSellStatus(
            @Valid
            @Parameter(name = "ids", description = "商品ID列表") @NotEmpty(message = "商品ID不能为空") ArrayList<Integer> ids,
            @Parameter(name = "sellStatus", description = "商品上下架状态") @NotNull(message = "商品上下架状不能为空") Integer sellStatus) {

        productService.batchUpdateSellStatus(ids, sellStatus);

        return ApiRestResponse.success();
    }

    @Operation(summary = "后台商品列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiRestResponse<Object> list(
            @Valid
            @Parameter(name = "pageNum", description = "页码") @NotNull(message = "页码不能为空") Integer pageNum,
            @Parameter(name = "pageSize", description = "每页查询数量") @NotNull(message = "每页查询数量不能为空") Integer pageSize) {

        return ApiRestResponse.success(productService.listForAdmin(pageNum, pageSize));
    }
}
