package com.zzy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-01-23
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getTeacherList();

    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);

}
