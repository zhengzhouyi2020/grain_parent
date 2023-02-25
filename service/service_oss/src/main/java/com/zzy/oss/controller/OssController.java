package com.zzy.oss.controller;

import com.zzy.oss.service.OssService;
import com.zzy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
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
@Api(value = "文件上传")
public class OssController {

    @Autowired
    public OssService ossService;

    @PostMapping
    @ApiParam(value = "文件上传实现")
    public R uploadOssFile(MultipartFile file) throws IOException {
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }


}
