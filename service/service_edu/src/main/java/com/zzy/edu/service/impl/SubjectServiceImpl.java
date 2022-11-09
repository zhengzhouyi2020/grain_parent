package com.zzy.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.edu.entity.Subject;
import com.zzy.edu.entity.vo.ExcelSubjectData;
import com.zzy.edu.entity.vo.OneSubject;
import com.zzy.edu.entity.vo.TwoSubject;
import com.zzy.edu.listener.SubjectExcelListener;
import com.zzy.edu.mapper.SubjectMapper;
import com.zzy.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try{
            InputStream inputStream= file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(0,"添加课程分类失败");
        }
    }

    //树形分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> list=new ArrayList<>();
        //查询所有的一级分类
        QueryWrapper<Subject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<Subject> oneSubjectList=baseMapper.selectList(wrapperOne);
        //查询多有的二级分类
        QueryWrapper<Subject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<Subject> twoSubjectList=baseMapper.selectList(wrapperTwo);
        //封装一级分类

        for (Subject subjectOne:oneSubjectList){
            OneSubject oneSubject=new OneSubject();
            ArrayList<TwoSubject> subjectTwoList=new ArrayList<>();
            TwoSubject twoSubject=new TwoSubject();

            oneSubject.setId(subjectOne.getId());
            oneSubject.setTitle(subjectOne.getTitle());
            for (Subject subjectTwo:twoSubjectList) {
                if(subjectTwo.getParentId().equals(subjectOne.getId())){
                    twoSubject.setId(subjectTwo.getId());
                    twoSubject.setTitle(subjectTwo.getTitle());
                }
            }
            subjectTwoList.add(twoSubject);
            oneSubject.setChildren(subjectTwoList);
            list.add(oneSubject);
        }
        //封装二级分类
        return list;
    }


}
