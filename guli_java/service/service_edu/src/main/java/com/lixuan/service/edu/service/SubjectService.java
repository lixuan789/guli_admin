package com.lixuan.service.edu.service;

import com.lixuan.service.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
}
