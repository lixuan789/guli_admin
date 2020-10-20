package com.lixuan.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.Teacher;
import com.lixuan.service.edu.service.CourseService;
import com.lixuan.service.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 查询前8条热门课程，查询前4条名师
 */
@RestController
@RequestMapping("/admin/front")
@CrossOrigin
public class IndexController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    //查询前8条热门课程，查询前4条名师
    @GetMapping("index")
    public Result index() {
        //查询前8条热门课程
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<Course> courseList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<Teacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<Teacher> teacherList = teacherService.list(wrapperTeacher);

        return Result.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
