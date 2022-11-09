package com.zzy.edu.service;

import com.zzy.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.edu.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-02
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();

}
