package com.zzy.demo;

import com.alibaba.excel.EasyExcel;

/**
 * @Description 读取excel文件
 * @Author Zzy
 * @Date 2021/2/3
 */
public class TestExcelRead {
    public static void main(String[] args) {
        String fileName="I:\\桌面/write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
