package com.zzy.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description excel学习
 * @Author Zzy
 * @Date 2021/2/3
 */
@Data
public class DemoData {

//    设置表头属性
    @ExcelProperty("学生编号")
    private Integer sno;

    @ExcelProperty("学生姓名")
    private String name;
}
