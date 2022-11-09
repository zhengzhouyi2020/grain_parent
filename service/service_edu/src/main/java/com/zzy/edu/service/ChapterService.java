package com.zzy.edu.service;

import com.zzy.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String id);
}
