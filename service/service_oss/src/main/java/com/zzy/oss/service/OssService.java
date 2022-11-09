package com.zzy.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description 文件上传Service层
 * @Author Zzy
 * @Date 2021/2/2
 */
public interface OssService {

    String uploadFileAvatar(MultipartFile file) throws IOException;
}
