package com.zzy.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @Description 常量类
 * @Author Zzy
 * @Date 2021/2/2
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置中的文件
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.key_id}")
    private String keyId;

    @Value("${aliyun.oss.file.key_secret}")
    private  String keySecret;

    @Value("${aliyun.oss.file.bucket_name}")
    private String bucketName;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SCERET;
    public static String BUCKET_NAME;


    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        ACCESS_KEY_ID= keyId;
        ACCESS_KEY_SCERET=keySecret;
        BUCKET_NAME=bucketName;
    }
}
