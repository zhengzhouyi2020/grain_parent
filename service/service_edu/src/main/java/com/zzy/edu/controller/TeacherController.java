package com.zzy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Teacher;
import com.zzy.edu.entity.vo.TeacherQuery;
import com.zzy.edu.service.TeacherService;
import com.zzy.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-01-23
 */
@Api("讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //    查询所有讲师的数据
    //rest风格
    @GetMapping("/findAllTeacher")
    @ApiOperation("讲师列表")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("逻辑删除讲师")
    public R deleteTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return flag ? R.ok() : R.error("code不能为空!");
    }

    @PostMapping("/teacherList")
    @ApiOperation("讲师分页")
    public R teacherList(@RequestParam("pageNo") long pageNo, @RequestParam("pageSize") long pageSize) {
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
        teacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("row", records);
    }


    //多条件查询及分页
    @PostMapping("/teacherListCondition/{pageNo}/{pageSize}")
    @ApiOperation("讲师条件分页")
    public R teacherListCondition(@PathVariable("pageNo") long pageNo, @PathVariable("pageSize") long pageSize, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
        //构建条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(teacherQuery.getName()), "name", teacherQuery.getName());
        queryWrapper.eq(teacherQuery.getLevel()!=null, "level", teacherQuery.getLevel());
        queryWrapper.ge(StringUtils.isNotBlank(teacherQuery.getBegin()), "gmt_create", teacherQuery.getBegin());
        queryWrapper.le(StringUtils.isNotBlank(teacherQuery.getEnd()), "gmt_modified", teacherQuery.getEnd());
        teacherService.page(teacherPage, queryWrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        return R.ok().data("total", total).data("row", records);
    }

    //添加讲师
    @PostMapping("/addTeacher")
    @ApiOperation("添加讲师")
    public R addTeacher(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error("code不能为空!");
    }

    //根据id查询讲师
    @GetMapping("/findById/{id}")
    @ApiOperation("查询讲师")
    public R findById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("teacher", teacher);
        }
        return R.error("code不能为空!");
    }

    //修改讲师
    @PostMapping("/updateTeacher")
    @ApiOperation("更新讲师")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        return flag ? R.ok() : R.error("code不能为空!");
    }


}

