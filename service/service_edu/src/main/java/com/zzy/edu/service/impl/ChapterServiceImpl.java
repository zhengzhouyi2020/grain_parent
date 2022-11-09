package com.zzy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.edu.entity.Chapter;
import com.zzy.edu.entity.Video;
import com.zzy.edu.entity.vo.ChapterVo;
import com.zzy.edu.entity.vo.VideoVo;
import com.zzy.edu.mapper.ChapterMapper;
import com.zzy.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    public VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程里面所有的章节
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<Chapter> chapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程id查询课程中的所有的小结
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<Video> videoList = videoService.list(wrapperVideo);

        List<ChapterVo> chapterVideoList = new ArrayList<>(); //返回的封装章节数据
        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo=new ChapterVo(); //新建章节对象
            BeanUtils.copyProperties(chapter,chapterVo);
            List<VideoVo> videoVoList=new ArrayList<>();
            for (Video video :videoList) {
                if(video.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo =new VideoVo();   //新建小结视频对象
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);  //将video对象放入列表中
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVideoList.add(chapterVo);
        }
        return chapterVideoList;
    }

//    删除章节,当有小节时，不能删除
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count=videoService.count(wrapper);
        if(count>0){
            throw new GlobalException(0,"章节不为空,不能删除");
        }else{
            int result=baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<Chapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",id);
        baseMapper.delete(queryWrapper);
    }
}
