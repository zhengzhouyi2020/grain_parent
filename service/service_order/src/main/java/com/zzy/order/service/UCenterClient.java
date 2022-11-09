package com.zzy.order.service;

import com.zzy.order.service.impl.UCenterClientImpl;
import com.zzy.utils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description 调用Ucenter服务
 * @Author Zzy
 * @Date 2021/2/26
 */
@Component
@FeignClient(name="service-ucenter",fallback = UCenterClientImpl.class)
public interface UCenterClient {
    // 根据用户id获取用户信息，返回用户信息对象
    @PostMapping("/ucenter/member//getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable("id") String id);

}
