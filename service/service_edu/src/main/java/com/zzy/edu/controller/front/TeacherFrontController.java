package com.zzy.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Course;
import com.zzy.edu.entity.Teacher;
import com.zzy.edu.service.CourseService;
import com.zzy.edu.service.TeacherService;
import com.zzy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description 前端讲师查询
 * @Author Zzy
 * @Date 2021/2/11
 */
@RestController
@RequestMapping("/edu/teacherFront")
@Api(value = "前端讲师",tags = "前端讲师")
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    // 分页查询讲师的方法
    @ApiOperation(value = "前端讲师分页查询",tags = "前端讲师分页查询")
    @PostMapping("/getFrontTeacherList/{pageNo}/{pageSize}")
    public R getFrontTeacherList(@PathVariable("pageNo") long pageNo, @PathVariable("pageSize") long pageSize){
        Page<Teacher> teacherPage=new Page<>(pageNo,pageSize);
        Map<String,Object> map=teacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);

    }

    //讲师查询的方法
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    @ApiOperation(value = "根据讲师id查询老师基本信息，和相关课程")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //根据id查询讲师信息
        Teacher teacher=teacherService.getById(teacherId);
        // 根据讲师信息查询课程信息
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        wrapper.orderByDesc("gmt_modified");
        List<Course> courseList=courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }

}
