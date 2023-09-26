package com.ianmu.mall.controller;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.common.Constant;
import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.request.AddProductReq;
import com.ianmu.mall.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * ProductAdminController 后台商品管理
 *
 * @author darre
 * @version 2023/09/24 21:08
 **/
@RestController
@RequestMapping("product/admin")
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
}
