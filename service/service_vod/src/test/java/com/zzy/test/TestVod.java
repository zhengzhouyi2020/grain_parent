package com.zzy.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;


import java.util.List;

/**
 * @Description 测试视频播放
 * @Author Zzy
 * @Date 2021/2/7
 */
public class TestVod {
    public static void main(String[] args) throws ClientException {
       String accessKeyId="LTAI4G4WqUU8YZrNTDcT2zLm";
       String accessKeySecret="GWOuP8zCEDKTSv2SJFl2ih3P3lYv8M";

       String title="首次上传的视频";
       String fileName="I:\\D盘文件\\人生不相见，动如参与商\\20171216_153648_742.mp4";
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
       /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
              其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
           */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public static void  getPlayAuth() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client=InitObject.initVodClient("LTAI4G4WqUU8YZrNTDcT2zLm","GWOuP8zCEDKTSv2SJFl2ih3P3lYv8M");
        //根据视频Id获取视频播放凭证
        GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();

        request.setVideoId("e0ea61ea72f14a839b670d890a3b28d1");
        //设置初始化对象，传递request,获取数据
        response=client.getAcsResponse(request);

        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");

    }

    public static void getPlayUrl() throws ClientException {
        //根据视频id获取视频的播放地址
        //创建初始化对象
        DefaultAcsClient client=InitObject.initVodClient("LTAI4G4WqUU8YZrNTDcT2zLm","GWOuP8zCEDKTSv2SJFl2ih3P3lYv8M");

        //向request对象设置视频id
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();
        request.setVideoId("e0ea61ea72f14a839b670d890a3b28d1");
        //设置初始化对象，传递request,获取数据
        response=client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
