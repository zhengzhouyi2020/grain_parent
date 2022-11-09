package com.zzy.edu.controller;


import com.zzy.edu.entity.Chapter;
import com.zzy.edu.entity.vo.ChapterVo;
import com.zzy.edu.service.ChapterService;
import com.zzy.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //课程大纲列表，根据课程id查询
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterList",list);
    }
    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("/findChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        Chapter chapter=chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("/{chapterId}")
    public R deleteById(@PathVariable String chapterId){
        Boolean flag=chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }




}

