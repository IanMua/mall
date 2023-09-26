package com.ianmu.mall.filter;

import com.ianmu.mall.common.ApiRestResponse;
import com.ianmu.mall.common.Assert;
import com.ianmu.mall.common.Constant;
import com.ianmu.mall.common.Jackson;
import com.ianmu.mall.exception.MallException;
import com.ianmu.mall.exception.MallExceptionEnum;
import com.ianmu.mall.model.pojo.User;
import com.ianmu.mall.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * AdminFilter 管理员校验过滤器
 *
 * @author darre
 * @version 2023/09/21 22:35
 **/
@WebFilter
@Slf4j
public class AdminFilter implements Filter {

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        // 校验是否为管理员
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        try {
            Assert.isNull(currentUser, MallExceptionEnum.NEED_LOGIN);
            Assert.isFalse(userService.checkAdminRole(currentUser), MallExceptionEnum.NEED_ADMIN);
        } catch (MallException ex) {
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = servletResponse.getWriter();
            out.write(Objects.requireNonNull(Jackson.ObjectToJson(ApiRestResponse.error(ex.getCode(), ex.getMessage()))));
            out.flush();
            out.close();
            return;
        }

        // 放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
