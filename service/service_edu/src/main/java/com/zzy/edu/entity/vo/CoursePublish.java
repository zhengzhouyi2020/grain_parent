package com.zzy.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 课程发布信息
 * @Author Zzy
 * @Date 2021/2/5
 */
@Data
public class CoursePublish implements Serializable {

    private String title;

    private String cover;

    private Integer lessonNum;

    private String subjectLevelOne;

    private String subjectLevelTwo;

    private String teacherName;

    private String price;

}
