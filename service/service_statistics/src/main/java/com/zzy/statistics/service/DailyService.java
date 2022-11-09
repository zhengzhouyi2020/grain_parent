package com.zzy.statistics.service;

import com.zzy.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
public interface DailyService extends IService<Daily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
