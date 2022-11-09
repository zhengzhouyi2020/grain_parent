package com.zzy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Teacher;
import com.zzy.edu.mapper.EduTeacherMapper;
import com.zzy.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-01-23
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<EduTeacherMapper, Teacher> implements TeacherService {

    @Cacheable(value = "teacher",key = "'teacherList'")
    @Override
    public List<Teacher> getTeacherList() {
        QueryWrapper<Teacher> wrapperTeacher =new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 8");
        List<Teacher> teacherList= baseMapper.selectList(wrapperTeacher);;
        return teacherList;
    }


    //分页查询讲师的方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        wrapper.orderByAsc("id");
        //把分页的数据放的map集合中
        baseMapper.selectPage(pageTeacher,wrapper);
        List<Teacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();
        //将分页数据放到集合中
        Map<String,Object> map=new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;


    }
}
