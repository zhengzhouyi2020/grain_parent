package com.zzy.oss.controller;

import com.zzy.oss.service.OssService;
import com.zzy.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description 上传
 * @Author Zzy
 * @Date 2021/2/2
 */
@RestController
@RequestMapping("/oss/file/upload")
public class OssController {

    @Autowired
    public OssService ossService;

    @PostMapping
    public R uploadOssFile(MultipartFile file) throws IOException {
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }


}
