package com.zzy.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author:zzy
 * @Date:2022/11/30
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.zzy")
@MapperScan("com.zzy.acl.mapper")
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }

}