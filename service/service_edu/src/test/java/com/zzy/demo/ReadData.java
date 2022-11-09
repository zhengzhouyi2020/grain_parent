package com.zzy.demo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description excel读操作
 * @Author Zzy
 * @Date 2021/2/3
 */
@Data
public class ReadData {
    @ExcelProperty(value = "学生编号",index=0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index=1)
    private String name;

}
