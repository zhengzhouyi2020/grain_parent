package com.zzy.order.service.impl;

import com.zzy.order.client.UCenterClient;
import com.zzy.utils.orderVo.UcenterMemberOrder;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/26
 */
@Component
public class UCenterClientImpl implements UCenterClient {
    @Override
    public UcenterMemberOrder getInfo(String id) {
        return null;
    }
}
