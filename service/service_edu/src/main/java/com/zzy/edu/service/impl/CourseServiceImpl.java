package com.zzy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.edu.entity.Course;
import com.zzy.edu.entity.CourseDescription;
import com.zzy.edu.entity.Subject;
import com.zzy.edu.entity.frontVo.CourseFrontVo;
import com.zzy.edu.entity.frontVo.CourseWebInfo;
import com.zzy.edu.entity.vo.CourseInfoVo;
import com.zzy.edu.entity.vo.CoursePublish;
import com.zzy.edu.mapper.CourseMapper;
import com.zzy.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;


    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表上添加课程信息
        Course course = new Course();
        Subject subject = subjectService.getById(courseInfoVo.getSubjectId());
        String subjectParentId = subject.getParentId();
        courseInfoVo.setSubjectParentId(subjectParentId);
        BeanUtils.copyProperties(courseInfoVo, course);


        //返回影响行数
        int insert = baseMapper.insert(course);
        if (insert == 0) {
            throw new GlobalException(0, "添加课程信息失败");
        }
        //获得课程id，放入课程简介表
        String cid = course.getId();

        //向课程简介表加上简介
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo findCourseById(String courseId) {

        //查询课程信息
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(baseMapper.selectById(courseId), courseInfoVo);

        //查询描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //修改课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update < 0) {
            throw new GlobalException(0, "修改课程信息失败");
        }
        //修改描述表
        CourseDescription description = new CourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
    }

    @Override
    public CoursePublish publishCourseInfo(String courseId) {
        CoursePublish coursePublish = baseMapper.getPublishInfo(courseId);
        return coursePublish;
    }

    @Override
    public void removeCourse(String id) {
        //格局根据课程id删除小节

        videoService.removeVideoByCourseId(id);
        // 根据课程id删除章节
        chapterService.removeChapterByCourseId(id);
        // 根据课程id删除课程描述
        courseDescriptionService.removeById(id);
        // 根据课程id删除课程
        int result=baseMapper.deleteById(id);
        if(result==0){
            throw new GlobalException(0,"删除成功");
        }
    }

    @Cacheable(value = "course",key = "'courseList'")
    @Override
    public List<Course> getCourseList() {
        QueryWrapper<Course> wrapperCourse =new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 4");
        return baseMapper.selectList(wrapperCourse);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageCourse, CourseFrontVo courseFrontVo) {


        QueryWrapper<Course> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(courseFrontVo.getSubjectParentId()),"subject_parent_id",courseFrontVo.getSubjectParentId());
        queryWrapper.eq(StringUtils.isNotBlank(courseFrontVo.getSubjectId()),"subject_id",courseFrontVo.getSubjectId());
        queryWrapper.orderByDesc(StringUtils.isNotBlank(courseFrontVo.getBuyCountSort()),"buy_count");
        queryWrapper.orderByDesc(StringUtils.isNotBlank(courseFrontVo.getGmtCreateSort()),"gmt_create");
        queryWrapper.orderByDesc(StringUtils.isNotBlank(courseFrontVo.getPriceSort()),"price");
        baseMapper.selectPage(pageCourse,queryWrapper);

        // 封装查询出的数据到map
        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;


    }

    @Override
    //用sql语句实现数据库查询
    public CourseWebInfo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
