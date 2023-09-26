package com.ianmu.mall.config;

import com.ianmu.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AdminFilterConfig Admin过滤器配置
 *
 * @author darre
 * @version 2023/09/22 20:55
 **/
@Configuration
public class MallAdminFilterConfig {

    @Bean
    public AdminFilter mallAdminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "mallAdminFilterConf")
    public FilterRegistrationBean<AdminFilter> adminFilterConfig() {
        FilterRegistrationBean<AdminFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(mallAdminFilter());
        filterRegistrationBean.addUrlPatterns("/category/admin/*");
        filterRegistrationBean.addUrlPatterns("/product/admin/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        filterRegistrationBean.setName("adminFilterConfig");

        return filterRegistrationBean;
    }
}
