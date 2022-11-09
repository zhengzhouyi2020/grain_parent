package com.zzy.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description 常量值使用
 * @Author Zzy
 * @Date 2021/2/8
 */
@Component
public class ConstantVodUtils  implements InitializingBean {

    @Value("${aliyun.vod.file.key_id}")
    private String keyId;
    @Value("${aliyun.vod.file.key_secret}")
    private String keySecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=keyId;
        ACCESS_KEY_SECRET=keySecret;

    }
}
