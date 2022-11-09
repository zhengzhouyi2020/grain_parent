package com.zzy.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description ucenter启动类
 * @Author Zzy
 * @Date 2021/2/10
 */
@SpringBootApplication
@MapperScan("com.zzy.ucenter.mapper")
@ComponentScan({"com.zzy"})
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
