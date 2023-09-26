package com.ianmu.mall.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig Swagger配置
 *
 * @author darre
 * @version 2023/09/20 21:59
 **/
@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Ian Mall OpenAPI")
                .description("Mall Project")
                .contact(new Contact())
                .version("1.0"));
    }
}
