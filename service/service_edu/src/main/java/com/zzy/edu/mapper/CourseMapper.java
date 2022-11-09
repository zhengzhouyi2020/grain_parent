package com.zzy.edu.mapper;

import com.zzy.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.edu.entity.frontVo.CourseWebInfo;
import com.zzy.edu.entity.vo.CoursePublish;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublish getPublishInfo(String courseId);

    public CourseWebInfo getBaseCourseInfo(String courseId);

}
