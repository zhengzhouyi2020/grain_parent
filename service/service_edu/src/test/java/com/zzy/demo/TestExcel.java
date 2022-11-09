package com.zzy.demo;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 测试excel
 * @Author Zzy
 * @Date 2021/2/3
 */
public class TestExcel {
    public static void main(String[] args) {
        //实现excel写操作
        //设置写入文件的地址和文件名称
        String fileName="I:\\桌面/write.xlsx";

        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());


    }
    //创建方法返回集合
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData data=new DemoData();
            data.setSno(i);
            data.setName("张华"+i);
            list.add(data);
        }
        return list;
    }
}
