package cn.cd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@Slf4j
@EnableSwagger2WebMvc
public class Knife4jConfig {

    // 访问地址：http://localhost:8080/doc.html
    /**
     * 【重要】【重要】【重要】 指定Controller包路径 类路径
     */
    private static final String BASE_PACKAGE = "cn.cd";
    /**
     * 组名
     */
    private static final String GROUP_NAME = "川大";
    /**
     * 主机名
     */
    private static final String HOST = "http://localhost";
    /**
     * 标题
     */
    private static final String TITLE = "测试-在线API文档";
    /**
     * 简介
     */
    private static final String DESCRIPTION = "测试-在线API文档";
    /**
     * 服务条款URL
     */
    private static final String TERMS_OF_SERVICE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 联系人
     */
    private static final String CONTACT_NAME = "Java教学研发部";
    /**
     * 联系网址
     */
    private static final String CONTACT_URL = "http://localhost:8080";
    /**
     * 联系邮箱
     */
    private static final String CONTACT_EMAIL = "tom@qq.cn";
    /**
     * 版本号
     */
    private static final String VERSION = "2.0";

    public Knife4jConfig() {
        log.debug("创建配置类对象：Knife4jConfiguration");
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(HOST)
                .apiInfo(apiInfo())
                .groupName(GROUP_NAME)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .contact(new Contact(CONTACT_NAME, CONTACT_URL, CONTACT_EMAIL))
                .version(VERSION)
                .build();
    }

}
