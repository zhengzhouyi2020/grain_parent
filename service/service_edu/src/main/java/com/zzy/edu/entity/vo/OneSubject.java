package com.zzy.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;

/**
 * @Description 一级目录
 * @Author Zzy
 * @Date 2021/2/2
 */
@Data
public class OneSubject {

    private String id;

    private String title;

    private ArrayList<TwoSubject> children=new ArrayList<>();
}
