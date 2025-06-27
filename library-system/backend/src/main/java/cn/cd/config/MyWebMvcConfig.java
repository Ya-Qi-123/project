package cn.cd.config;

import cn.cd.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    private static List< String> excludePaths = Arrays.asList("/alllogin", "/login");

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")             // 拦截/开头的所有路径
                .excludePathPatterns(excludePaths); // 排除指定路径
    }
}
