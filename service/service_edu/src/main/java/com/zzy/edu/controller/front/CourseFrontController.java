package com.zzy.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Chapter;
import com.zzy.edu.entity.Course;
import com.zzy.edu.entity.frontVo.CourseFrontVo;
import com.zzy.edu.entity.frontVo.CourseWebInfo;
import com.zzy.edu.entity.vo.ChapterVo;
import com.zzy.edu.service.ChapterService;
import com.zzy.edu.service.CourseService;
import com.zzy.utils.R;
import com.zzy.utils.orderVo.CourseOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 前台课程信息展示controller层
 * @Author Zzy
 * @Date 2021/2/11
 */
@RestController
@Api(value = "前端课程",tags = "前端课程")
@RequestMapping("/edu/courseFront")
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    // 分页显示课程信息
    @ApiOperation("课程条件查询")
    @PostMapping("/getFrontCourseList/{pageNo}/{pageSize}")
    public R getFrontCourseList(@PathVariable long pageNo, @PathVariable  long pageSize, @RequestBody CourseFrontVo courseFrontVo){
        Page<Course> pageCourse=new Page<>(pageNo,pageSize);
        Map<String,Object> map=courseService.getCourseFrontList(pageCourse,courseFrontVo);
        return R.ok().data("map",map);

    }

//    课程查询的方法
    @PostMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //根据id查询sql课程信息
        CourseWebInfo courseWebInfo=courseService.getBaseCourseInfo(courseId);
        //根据id查询章节和小节
        List<ChapterVo> chapterVideoList=chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo",courseWebInfo).data("chapterVideoList",chapterVideoList);
    }
    //根据课程id查询课程信息

    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseOrderVo getCourseInfoOrder(@PathVariable String id){
        CourseWebInfo courseWebInfo=courseService.getBaseCourseInfo(id);
        CourseOrderVo courseOrderVo=new CourseOrderVo();
        BeanUtils.copyProperties(courseWebInfo,courseOrderVo);
        return courseOrderVo;
    }
}
