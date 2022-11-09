package com.zzy.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.utils.R;
import com.zzy.vod.service.VodService;
import com.zzy.vod.utils.ConstantVodUtils;
import com.zzy.vod.utils.InitVodClient;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description 视频上传controller层
 * @Author Zzy
 * @Date 2021/2/7
 */
@RestController
@RequestMapping("/vod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadVideo")
    public R uploadAliyunVideo(@RequestBody MultipartFile file) throws IOException {
        //返回视频上传的id
        String videoId=vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    // 根据视频id从阿里云删除
    @DeleteMapping("/{videoId}")
    public R deleteVideoById(@PathVariable String videoId){
        try{
            DefaultAcsClient client=InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            response=client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(0,"删除视频失败");
        }
    }
    // 删除多个视频
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam List<String> videoList){
        vodService.removeByIds(videoList);
        return R.ok();
    }

//    根据视频id获取视频播放权限
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
            DefaultAcsClient client=InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response=client.getAcsResponse(request);
            String playAuth=response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GlobalException(0,"获取凭证失败");
        }
    }
}
