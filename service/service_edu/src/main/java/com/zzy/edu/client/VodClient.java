package com.zzy.edu.client;

import com.zzy.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 微服务调用
 * @Author Zzy
 * @Date 2021/2/8
 */
@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)//调用服务的名称
public interface VodClient {
    // 删除单个视频
    @DeleteMapping("/vod/video/{videoId}")
    public R deleteVideoById(@PathVariable(name = "videoId") String videoId);
    // 删除多个视频集合
    @DeleteMapping("/vod/video/deleteBatch")
    public R deleteBatch(@RequestParam List<String> videoList);
}
