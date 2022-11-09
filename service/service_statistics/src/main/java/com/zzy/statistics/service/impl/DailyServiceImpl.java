package com.zzy.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.statistics.entity.Daily;
import com.zzy.statistics.mapper.DailyMapper;
import com.zzy.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.statistics.service.UcenterClient;
import com.zzy.utils.R;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-26
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        QueryWrapper<Daily> wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R registerR=ucenterClient.countRegister(day);

        Integer countRegister= (Integer) registerR.getData().get("countRegister");

        //模拟数据
        Integer loginNum= RandomUtils.nextInt(100,200);
        Integer videoViewNum=RandomUtils.nextInt(100,200);
        Integer courseNum=RandomUtils.nextInt(100,200);

        Daily daily=new Daily();
        daily.setCourseNum(courseNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setDateCalculated(day);
        daily.setRegisterNum(courseNum);

        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> queryWrapper=new QueryWrapper<>();
        queryWrapper.between("data_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        List<Daily> dailyList=baseMapper.selectList(queryWrapper);

        //前台需要json数据，对应后端list集合
        //创建日期数据list，数量list
        List<String>dateList=new ArrayList<>();
        List<Integer> numDataList=new ArrayList<>();
        for(int i=0;i<dailyList.size();i++){
            Daily daily=dailyList.get(i);
            dateList.add(daily.getDateCalculated());
            switch(type){
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map=new HashMap<>();
        map.put("dateList",dateList);
        map.put("numDateList",numDataList);
        return map;

    }
}
