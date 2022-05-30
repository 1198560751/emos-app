package com.example.emos.wx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

// 配置类
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfoBuilder infoBuilder = new ApiInfoBuilder();
        //标题
        infoBuilder.title("EMOS在线办公系统");
        ApiInfo info = infoBuilder.build();
        docket.apiInfo(info);
        ApiSelectorBuilder selectorBuilder = docket.select();
        //设置那些类那些方法添加swagger页面
        // ------ 所有的类都添加到swagger
        selectorBuilder.paths(PathSelectors.any());
        // ------ 需要使用ApiOption注解的才显示到页面上
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        // 重新构建docket
        docket = selectorBuilder.build();

        // 让swagger支持JWT         (name:支持的key, keyName:描述, passAs:"在哪里出现")
        ApiKey apiKey = new ApiKey("token", "token", "header");
        List<ApiKey> apiKeys = new ArrayList<ApiKey>();
        apiKeys.add(apiKey);
        docket.securitySchemes(apiKeys);

        //设定swagger作用域      (scope: 全局, description:描述)
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = {scope};
        SecurityReference reference = new SecurityReference("token", scopes);
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(reference);
        SecurityContext context = SecurityContext.builder().securityReferences(securityReferenceList).build();
        List<SecurityContext> ctxList = new ArrayList<>();
        ctxList.add(context);
        docket.securityContexts(ctxList);
        return docket;
    }

}
