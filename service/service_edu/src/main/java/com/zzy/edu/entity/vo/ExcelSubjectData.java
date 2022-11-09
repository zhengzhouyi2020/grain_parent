package com.zzy.edu.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Description excel实体类
 * @Author Zzy
 * @Date 2021/2/2
 */
@Data
public class ExcelSubjectData {

    @ExcelProperty(index=0)
    private String oneSubjectName;

    @ExcelProperty(index=2)
    private String twoSubjectName;


}
