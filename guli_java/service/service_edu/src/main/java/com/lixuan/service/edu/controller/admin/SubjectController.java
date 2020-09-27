package com.lixuan.service.edu.controller.admin;


import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Subject;
import com.lixuan.service.edu.entity.subject.OneSubject;
import com.lixuan.service.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * 放回树形菜单
     * @return
     */
    @GetMapping("list")
    public Result getAllSubject(){
//        List<OneSubject> list=subjectService.getAllSubject();//使用两个实体类实现
        List<Subject> list=subjectService.getAllSubject1();//优化：使用递归实现
        return Result.ok().data("list",list);
    }


}

