package com.zzy.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.edu.entity.Course;
import com.zzy.edu.entity.Teacher;
import com.zzy.edu.service.CourseService;
import com.zzy.edu.service.TeacherService;
import com.zzy.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description 前台获取数据
 * @Author Zzy
 * @Date 2021/2/9
 */
@RestController
@RequestMapping("/edu/indexFront")
public class IndexFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;
    // 查询前面八条热门课程
    @GetMapping("/index")
    public R index(){
        List<Course> courseList= courseService.getCourseList();
        List<Teacher> teacherList= teacherService.getTeacherList();
        return R.ok().data("teacherList",teacherList).data("courseList",courseList);
    }
    // 查询前面四条名师数据
}
