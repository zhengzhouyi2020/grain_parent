package com.zzy.msm.service;

import java.util.Map;

/**
 * @Description service层
 * @Author Zzy
 * @Date 2021/2/9
 */
public interface MsmService {
    // 发送短信的方法
    boolean send(Map<String, Object> params, String phone);
}
