package com.lin.backend_test.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@EnableSwagger2
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.lin"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("测试系统用户模块")
//                .description("用户模块api")
//                .version("1.0")
//                .build();
//    }
//}

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("测试系统用户模块")
                        .description("用户模块api")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("项目API文档")
                        .url("/"));
    }
//    swagger接口默认地址:localhost:port/swagger-ui/index.html
//    knife4j接口默认地址：localhost:port/doc.html
}
