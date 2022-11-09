package com.zzy.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description 分页参数定义
 * @Author Zzy
 * @Date 2021/1/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPage implements Serializable {

    //当前页
    private int pageNo;

    //每页记录数
    private int pageSize;
}
