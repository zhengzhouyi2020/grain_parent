package com.zzy.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.utils.R;
import com.zzy.vod.service.VodService;
import com.zzy.vod.utils.ConstantVodUtils;
import com.zzy.vod.utils.InitVodClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/7
 * accessKeyId:id
 * accessKeySecret:密钥
 * title:上传之后的显示名称
 * fileName:上传前的原始名称
 * inputStream: 输入流，一般是将原始的文件转化为输入流
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) throws IOException {
        String accessKeyId= ConstantVodUtils.ACCESS_KEY_ID;
        String accessKeySecret=ConstantVodUtils.ACCESS_KEY_SECRET;

        //文件名称
        String fileName=file.getOriginalFilename();
        //上传后的文件名字
        String title= Objects.requireNonNull(file.getOriginalFilename()).substring(0,fileName.lastIndexOf('.'));
        InputStream inputStream=file.getInputStream();
        String videoId=null;
        try{
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }

        return videoId;
    }

    @Override
    public void removeByIds(List<String> videoList) {
        try{
            //初始化
            DefaultAcsClient client= InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建视频删除对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            //支持传入多个视频ID，多个用逗号分隔
            String videoIds= StringUtils.join(videoList.toArray(),',');
            request.setVideoIds(videoIds);
            response=client.getAcsResponse(request);
        }catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(0,"删除视频失败");
        }
    }
}
