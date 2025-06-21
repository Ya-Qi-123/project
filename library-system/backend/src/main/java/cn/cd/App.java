package cn.cd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 启动类,申明当前类是SpringBoot项目的启动类，默认端口是8080
@SpringBootApplication
// 扫描mapper接口所在的包，生成代理对象，保存到IOC容器中，后续给Service注入使用
@MapperScan("cn.cd.mapper")
public class App {
    public static void main(String[] args) {
        //使用启动类App去启动一个Spring应用
        SpringApplication.run(App.class, args);
    }
}
