package com.ianmu.mall.controller;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.common.Constant;
import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.pojo.User;
import com.ianmu.mall.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController 用户
 *
 * @author darre
 * @version 2023/09/17 10:21
 **/
@Tag(name = "用户", description = "用户管理")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 注册
     *
     * @param userName 用户名
     * @param password 密码
     * @return 响应
     */
    @Operation(summary = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiRestResponse<Object> register(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password) {

        Assert.isEmpty(userName, MallExceptionEnum.NEED_USER_NAME);
        Assert.isEmpty(password, MallExceptionEnum.NEED_PASSWORD);
        if (password.length() < 8) {
            throw new MallException(MallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName, password);

        return ApiRestResponse.success();
    }

    @Operation(summary = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiRestResponse<User> login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session) {

        Assert.isEmpty(userName, MallExceptionEnum.NEED_USER_NAME);
        Assert.isEmpty(password, MallExceptionEnum.NEED_PASSWORD);

        User user = userService.login(userName, password);
        user.setPassword(null);

        // 设置Session
        session.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(user);
    }

    @Operation(summary = "用户个性签名更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiRestResponse<Object> updateUserInfo(
            HttpSession session,
            @RequestParam("signature") String signature) {

        // 获取session中的用户信息
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        Assert.isNull(currentUser, MallExceptionEnum.NEED_LOGIN);

        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);

        userService.updateInformation(user);

        return ApiRestResponse.success();
    }

    @Operation(summary = "用户登出")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ApiRestResponse<Object> logout(HttpSession session) {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }

    @Operation(summary = "管理员登录")
    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public ApiRestResponse<User> adminLogin(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session) {

        Assert.isEmpty(userName, MallExceptionEnum.NEED_USER_NAME);
        Assert.isEmpty(password, MallExceptionEnum.NEED_PASSWORD);

        User user = userService.login(userName, password);

        Assert.isFalse(userService.checkAdminRole(user), MallExceptionEnum.NEED_ADMIN);

        user.setPassword(null);

        // 设置Session
        session.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(user);
    }
}
