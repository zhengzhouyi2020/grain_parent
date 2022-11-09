package com.zzy.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zzy.oss.service.OssService;
import com.zzy.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/2
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SCERET;
        String bucketName=ConstantPropertiesUtils.BUCKET_NAME;

        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
            InputStream inputStream = file.getInputStream();
            String fileName=file.getOriginalFilename();

            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            String newFileName=uuid+fileName;

            String datePath=new DateTime().toString("yyyy/MM/dd");
            newFileName=datePath+newFileName;

            ossClient.putObject(bucketName, newFileName, inputStream);

// 关闭OSSClient。
            ossClient.shutdown();

            return "https://"+bucketName+"."+endpoint+"/"+newFileName;

        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
