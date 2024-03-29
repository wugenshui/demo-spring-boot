package com.github.wugenshui.knife4j.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : chenbo
 * @date : 2020-02-09
 */
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Configuration {

    /**
     * 版本号
     */
    public static final String VERSION = "1.0.0";

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        List<GrantType> grantTypes = new ArrayList<>();
        // 密码模式
        String passwordTokenUrl = "http://localhost:8080/oauth/token";
        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
        grantTypes.add(resourceOwnerPasswordCredentialsGrant);

        OAuth oAuth = new OAuthBuilder().name("oauth2")
                .grantTypes(grantTypes).build();
        // scope
        List<AuthorizationScope> scopes = new ArrayList<>();
        scopes.add(new AuthorizationScope("read", "read all resources"));
        scopes.add(new AuthorizationScope("write", "write all resources"));
        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
        SecurityContext securityContext = new SecurityContext(Arrays.asList(securityReference), PathSelectors.ant("/api/**"));
        // schemes
        List<SecurityScheme> securitySchemes = Arrays.asList(oAuth);
        // contexts
        List<SecurityContext> securityContexts = Arrays.asList(securityContext);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("api-swagger")
                        .description("swagger RESTful APIs")
                        .termsOfServiceUrl("https://www.github.wugenshui/")
                        .contact(new Contact("chenbo", "", ""))
                        .version(VERSION)
                        .build())
                //分组名称
                .groupName("通用接口")
                .select()
                // 这里指定 API 扫描方式
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts).securitySchemes(securitySchemes);
        ;
        return docket;
    }
}