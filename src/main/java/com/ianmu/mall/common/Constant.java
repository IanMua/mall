package com.ianmu.mall.common;

import io.lettuce.core.internal.LettuceSets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Constant 常量
 *
 * @author darre
 * @version 2023/09/17 19:02
 **/
@Component
public class Constant {

    /**
     * 盐值
     */
    public static final String SALT = "wiu8g928fh/.`i]44/'9452-1-34u4jf";

    /**
     * Session的key
     */
    public static final String MALL_USER = "mall_user";

    /**
     * 商品图片上传地址
     */
    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = LettuceSets.newHashSet("price desc", "price asc");
    }
}
