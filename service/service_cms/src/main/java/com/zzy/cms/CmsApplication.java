package com.zzy.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description cms启动类
 * @Author Zzy
 * @Date 2021/2/9
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zzy"})
@MapperScan("com.zzy.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        try{
            SpringApplication.run(CmsApplication.class,args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
