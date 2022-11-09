package com.zzy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Course;
import com.zzy.edu.entity.Teacher;
import com.zzy.edu.entity.vo.CourseInfoVo;
import com.zzy.edu.entity.vo.CoursePublish;
import com.zzy.edu.entity.vo.CourseQuery;
import com.zzy.edu.service.CourseService;
import com.zzy.utils.R;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
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
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //添加课程信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //查询所有的课程信息
    @GetMapping("/findAllCourse")
    public R findAllCourse(){
        return R.ok();
    }

    //根据id查询课程信息
    @GetMapping("/findCourseById/{courseId}")
    public R findCourseById(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.findCourseById(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    // 更新课程信息
    @PostMapping("/updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){
            courseService.updateCourseInfo(courseInfoVo);
            return R.ok();
    }

    //查询所有的发布课程信息
    @GetMapping("/getPublishInfo/{courseId}")
    public R getPublishInfo(@PathVariable String courseId){
        CoursePublish coursePublish=courseService.publishCourseInfo(courseId);
        return R.ok().data("publishCourse",coursePublish);
    }

    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course=new Course();
        course.setId(id);
        course.setStatus("Normal");//设置课程状态
        boolean saveSuccess=courseService.updateById(course);
        if(saveSuccess){
            return R.ok();
        }else{
            return R.error().message("课程发布失败");
        }
    }

    // 查询课程列表
    @GetMapping
    public R getCourseList(){
        List<Course> list=courseService.list(null);
        return R.ok().data("list",list);
    }

    //多条件查询+分页
    @PostMapping("/pageCourseCondition/{pageNo}/{pageSize}")
    @ApiOperation(value="分页课程列表")
    public R pageCondition(@PathVariable("pageNo") long pageNo, @PathVariable("pageSize") long pageSize, @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> coursePage=new Page<>(pageNo,pageSize);
        // 构建查询条件
        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(courseQuery.getTitle()),"title",courseQuery.getTitle());
        queryWrapper.eq(StringUtils.isNotBlank(courseQuery.getStatus()),"status",courseQuery.getStatus());
        courseService.page(coursePage,queryWrapper);
        long total=coursePage.getTotal();
        List<Course> records=coursePage.getRecords();
        return R.ok().data("total",total).data("row",records);
    }

    //删除课程
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable String id){
       courseService.removeCourse(id);
        return R.ok();
    }

    //根据id查询讲师
    @GetMapping("/findById/{id}")
    @ApiOperation("查询课程")
    public R findById(@PathVariable String id) {
        Course course = courseService.getById(id);
        if (course != null) {
            return R.ok().data("course", course);
        }
        return R.error();
    }



}

