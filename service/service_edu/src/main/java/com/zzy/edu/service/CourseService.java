package com.zzy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.edu.entity.frontVo.CourseFrontVo;
import com.zzy.edu.entity.frontVo.CourseWebInfo;
import com.zzy.edu.entity.vo.CourseInfoVo;
import com.zzy.edu.entity.vo.CoursePublish;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
public interface CourseService extends IService<Course> {

    //保存课程信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据id查询课程信息
    CourseInfoVo findCourseById(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublish publishCourseInfo(String courseId);

    void removeCourse(String id);

    List<Course> getCourseList();

    Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebInfo getBaseCourseInfo(String courseId);
}
