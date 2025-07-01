package cn.cd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
@Slf4j
@EnableKnife4j // 启用Knife4j增强功能
public class Knife4jConfig {

    // 访问地址：http://localhost:8080/doc.html
    private static final String BASE_PACKAGE = "cn.cd";  // Controller包路径
    private static final String GROUP_NAME = "川大";     // 分组名称
    private static final String TITLE = "测试-在线API文档";
    private static final String DESCRIPTION = "测试-在线API文档";
    private static final String TERMS_OF_SERVICE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
    private static final String CONTACT_NAME = "Java教学研发部";
    private static final String CONTACT_URL = "http://localhost:8080";
    private static final String CONTACT_EMAIL = "tom@qq.cn";
    private static final String VERSION = "2.0";

    public Knife4jConfig() {
        log.debug("创建配置类对象：Knife4jConfiguration");
    }

    /**
     * 配置API分组和扫描规则（OpenAPI 3.0方式）
     */
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group(GROUP_NAME)
                .packagesToScan(BASE_PACKAGE)  // 扫描Controller包
                .build();
    }

    /**
     * 配置OpenAPI基础信息（替代原Swagger的Docket）
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        .contact(new Contact()
                                .name(CONTACT_NAME)
                                .url(CONTACT_URL)
                                .email(CONTACT_EMAIL))
                        .license(new License()
                                .name("Apache 2.0")
                                .url(TERMS_OF_SERVICE_URL)));
    }
}
//package cn.cd.config;
//
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//
//@Configuration
//@EnableKnife4j // 启用Knife4j
//public class Knife4jConfig {
//
//    // SpringDoc分组配置（原OpenApiConfig内容）
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("bookstore") // API分组名称
//                .pathsToMatch("/**") // 扫描所有控制器路径
//                .build();
//    }
//
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//                .openapi("3.0.3")
//                .info(new Info()
//                        .title("API文档")
//                        .description("项目接口文档")
//                        .version("1.0.0")
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
//    }
//}
