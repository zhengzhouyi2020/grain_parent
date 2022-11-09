package com.zzy.edu.client;

import com.zzy.edu.entity.vo.UcMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description 微服务调用接口
 * @Author Zzy
 * @Date 2021/2/25
 */

@Component
@FeignClient(name ="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    //根据id获取用户信息
    @PostMapping("/ucenter/member/getInfoUc/{id}")
    public UcMemberVo getInfo(@PathVariable("id") String id);
}
