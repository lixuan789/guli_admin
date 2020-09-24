package com.lixuan.service.edu.controller;


import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/edu/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传的Excel文件，读取并插入到数据库中
     * @param file
     * @return
     */
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return Result.ok();
    }

}

