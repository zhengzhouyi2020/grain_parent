package com.zzy.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 章节封装chapter
 * @Author Zzy
 * @Date 2021/2/3
 */
@Data
public class ChapterVo {

    private String id;

    private String title;

    private Integer sort;

    //表示小结
    private List<VideoVo> children =new ArrayList<>();
}
