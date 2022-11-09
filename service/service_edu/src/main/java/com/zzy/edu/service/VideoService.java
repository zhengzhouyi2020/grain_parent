package com.zzy.edu.service;

import com.zzy.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String id);
}
