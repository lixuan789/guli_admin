package com.lixuan.service.edu.service;

import com.lixuan.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lixuan.service.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 添加课程分类
     * @param file
     * @param subjectService
     */
    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllSubject();

    /**
     * 使用递归实现树形菜单
     * @return
     */
    List<Subject> getAllSubject1();
}
