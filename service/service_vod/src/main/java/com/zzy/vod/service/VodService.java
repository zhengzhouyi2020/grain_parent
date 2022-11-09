package com.zzy.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description 视频上传Servide接口
 * @Author Zzy
 * @Date 2021/2/7
 */
public interface VodService {
    String uploadVideo(MultipartFile file) throws IOException;

    void removeByIds(List<String> videoList);
}
