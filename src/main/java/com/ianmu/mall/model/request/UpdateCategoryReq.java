package com.ianmu.mall.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * UpdateCategoryReq 类描述
 *
 * @author darre
 * @version 2023/9/21 21:52
 **/
@Schema(name = "UpdateCategoryReq", description = "更新目录请求类")
@Slf4j
@Data
public class UpdateCategoryReq {

    @Schema(name = "id", description = "目录ID")
    @NotNull
    private Integer id;

    /**
     * 目录名称
     */
    @Schema(name = "name", description = "目录名称")
    @NotBlank(message = "目录名称不能为空")
    @Size(min = 2, max = 5, message = "目录名称应为2-5个字符")
    private String name;

    /**
     * 目录类型
     */
    @Schema(name = "type", description = "目录类型")
//    @NotNull(message = "目录类型不能为空")
    @Max(value = 3, message = "目录类型最大为3")
    @Min(value = 1, message = "目录类型最小为1")
    private Integer type;

    /**
     * 父目录ID
     */
    @Schema(name = "parentId", description = "父目录ID")
//    @NotNull(message = "父目录不能为空")
    private Integer parentId;

    /**
     * 排序
     */
    @Schema(name = "orderNum", description = "目录排序")
//    @NotNull(message = "目录排序不能为空")
    private Integer orderNum;
}
