package com.zzy.statistics.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description 日期时间操作类
 * @Author Zzy
 * @Date 2021/2/26
 */
public class DateUtil {

    public static String formatDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat();
        return sdf.format(date);
    }

    /**
     * 在日期date上增加amount天 。
     *
     * @param date   处理的日期，非null
     * @param amount 要加的天数，可能为负数
     */
    public static Date addDays(Date date, int amount) {
        Calendar now =Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+amount);
        return now.getTime();
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.formatDate(new Date()));
        System.out.println(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
