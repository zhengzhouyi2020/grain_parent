package com.zzy.edu.client;

import com.zzy.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/8
 */
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideoById(String videoId) {
        return R.error().message("删除视频失败");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频失败");
    }
}
