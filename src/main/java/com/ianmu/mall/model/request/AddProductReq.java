package com.ianmu.mall.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "AddProductReq", description = "添加商品请求类")
@Data
public class AddProductReq {

    @Schema(name = "name", description = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    @Schema(name = "image", description = "商品图片")
    @NotBlank(message = "商品图片不能为空")
    private String image;

    @Schema(name = "detail", description = "商品详情")
    private String detail;

    @Schema(name = "categoryId", description = "商品分类ID")
    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;

    @Schema(name = "price", description = "商品价格")
    @NotNull(message = "商品价格不能为空")
    @Min(value = 1, message = "商品价格不能小于1分")
    private Integer price;

    @Schema(name = "stock", description = "商品库存")
    @NotNull(message = "商品库存不能为空")
    @Max(value = 10000, message = "商品库存不能大于10000")
    @Min(value = 1, message = "商品库存不能小于1")
    private Integer stock;

    @Schema(name = "status", description = "商品状态")
    private Integer status;
}