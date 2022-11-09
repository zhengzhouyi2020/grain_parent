package com.zzy.statistics.service;

import com.zzy.statistics.service.impl.UcenterClientImpl;
import com.zzy.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/26
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping("/ucenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
