package com.github.wugenshui.baseutil.baseutil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author : chenbo
 * @date : 2019/12/5
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 设置为当前项目控制器路径
                .apis(RequestHandlerSelectors.basePackage("com.chenbo.daomybatisplus.auth.controller"))
                .paths(PathSelectors.any())
                .build();
        // return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
    }

    /**
     * Api文档的详细信息函数
     *
     * @return ApiInfo api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Util工具模块Swagger文档")
                // 描述
                .description("API 描述")
                // 创建人
                .contact(new Contact("ChenBo", "https://github.com/wugenshui", "chenboprogrammr@gmail.com"))
                // 版本号
                .version("1.0")
                .build();
    }
}
