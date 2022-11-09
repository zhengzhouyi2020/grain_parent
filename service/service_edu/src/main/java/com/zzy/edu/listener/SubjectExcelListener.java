package com.zzy.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.edu.entity.Subject;
import com.zzy.edu.entity.vo.ExcelSubjectData;
import com.zzy.edu.service.SubjectService;

/**
 * @Description
 * @Author Zzy
 * @Date 2021/2/3
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener(){

    }
    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService=subjectService;
    }

    //读取excel文件一行一行读取
    @Override
    public void invoke(ExcelSubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GlobalException(0,"文件数据为空");

        }
        //一行一行读取，每次读取两个值，第一个值一级分类，第二个值二级分类
        // 一级分类
        Subject oneSubject=this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if(oneSubject==null){
            oneSubject=new Subject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }

        String pid=oneSubject.getId();
        //二级分类
        Subject twoSubject=this.existTwoSubject(subjectService,subjectData.getTwoSubjectName(),pid);
        if(twoSubject==null){
            twoSubject=new Subject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getOneSubjectName());//二级分类名称
            subjectService.save(twoSubject);
        }




    }

    //判断一级分类不能重复添加

    private Subject existOneSubject(SubjectService subjectService,String name){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        Subject serviceOne = subjectService.getOne(wrapper);
        return serviceOne;
    }
    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService,String name,String pid){
        QueryWrapper<Subject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        Subject serviceTwo = subjectService.getOne(wrapper);
        return serviceTwo;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
