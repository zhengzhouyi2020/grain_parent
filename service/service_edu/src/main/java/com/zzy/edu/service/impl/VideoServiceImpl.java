package com.zzy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.edu.client.VodClient;
import com.zzy.edu.entity.Video;
import com.zzy.edu.mapper.VideoMapper;
import com.zzy.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String id) {
        //根据课程查询所有的视频存储id
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
       // wrapper.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(wrapper);

        List<String> videoSourceIds = new ArrayList<>();
        for (Video video : videoList) {
            videoSourceIds.add(video.getVideoSourceId());
        }
        System.out.println(videoSourceIds);
        if(videoSourceIds.size()>0){
            // 根据视频ids删除所有的视频
            vodClient.deleteBatch(videoSourceIds);
        }
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        baseMapper.delete(queryWrapper);
    }
}
