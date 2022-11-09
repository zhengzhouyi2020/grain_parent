package com.zzy.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 搜索对象
 * @Author Zzy
 * @Date 2021/2/5
 */
@Data
@ApiModel(value = "Course查询对象",description = "课程查询对象封装")
public class CourseQuery  implements Serializable {

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程状态 Draft未发布 Normal已发布")
    private String status;


}
