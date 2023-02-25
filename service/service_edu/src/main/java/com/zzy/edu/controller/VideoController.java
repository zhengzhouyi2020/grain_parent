package com.zzy.edu.controller;


import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.edu.client.VodClient;
import com.zzy.edu.entity.Video;
import com.zzy.edu.entity.vo.VideoVo;
import com.zzy.edu.service.VideoService;
import com.zzy.utils.R;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.objects.Global;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/edu/video")
@Api(value = "小节管理",tags = "小节管理")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

//    添加视频小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video  video){
        boolean success=videoService.save(video);
        if(!success){
            throw new GlobalException(0,"添加视频失败");
        }
        else{
            return R.ok().message("添加视频成功");
        }
    }
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video){
        boolean success=videoService.updateById(video);
        if(success){
            return R.ok().message("修改视频小节成功");
        }else{
            throw new GlobalException(0,"修改视频小节失败");
        }
    }

    @GetMapping("/findVideoById/{videoId}")
    public R findVideoById(@PathVariable String videoId ){
        Video video =videoService.getById(videoId);
        return R.ok().data("video",video);
    }

    @DeleteMapping("/{videoId}")
    public  R deleteVideoById(@PathVariable String videoId){
        // 首先根据视频id查询到在阿里云中的存储id
        String videoSourceId=videoService.getById(videoId).getVideoSourceId();
        // 远程调用接口删除阿里云视频
        // 判断是否为空，否则调用函数完成删除
        if(StringUtils.isNotBlank(videoSourceId)){
            vodClient.deleteVideoById(videoSourceId);

        }
        boolean success=videoService.removeById(videoId);

        if(success){
            return R.ok().message("删除视频成功");
        }else{
            throw  new GlobalException(0,"删除视频失败");
        }
    }


}

