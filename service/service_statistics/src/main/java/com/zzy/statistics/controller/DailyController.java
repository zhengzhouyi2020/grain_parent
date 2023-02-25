package com.zzy.statistics.controller;


import com.zzy.statistics.service.DailyService;
import com.zzy.utils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 *
 */
@RestController
@RequestMapping("/statistics/daily")
@Api(value = "统计管理",tags = "统计管理")
public class DailyController {

    @Autowired
    public DailyService dailyService;

    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day){
        dailyService.registerCount(day);
        return R.ok();
    }

    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){
        Map<String ,Object> map=dailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }


}

