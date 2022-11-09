package com.zzy.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description springboot启动类
 * @Author Zzy
 * @Date 2021/1/23
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zzy"})
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        try{
            SpringApplication.run(EduApplication.class,args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
