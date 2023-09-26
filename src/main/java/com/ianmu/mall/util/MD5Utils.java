package com.ianmu.mall.util;

import com.ianmu.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5Utils 加密工具
 *
 * @author darre
 * @version 2023/09/17 18:53
 **/
public class MD5Utils {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String md5 = getMD5Str("1234");
        System.out.println(md5);
    }

    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));
    }
}
