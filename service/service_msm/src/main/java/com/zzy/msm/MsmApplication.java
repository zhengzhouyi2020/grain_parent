package com.zzy.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description Msm启动类
 * @Author Zzy
 * @Date 2021/2/9
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)// 取消数据源自动配置
@ComponentScan(basePackages = {"com.zzy"})
@EnableDiscoveryClient
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }
}
