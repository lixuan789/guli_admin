package com.lixuan.service.edu.controller.admin;


import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.vo.CourseInfoVo;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import com.lixuan.service.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;


    /**
     * 添加课程的基本信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody(required = true)CourseInfoVo courseInfoVo){
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);
    }

    /**
     * 获取课程的基本信息
     * @param courseId
     * @return
     */
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable(value = "courseId",required = true) String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo",courseInfoVo);
    }

    /**
     * 更新课程的基本信息
     * @param courseInfoVo
     * @return
     */
    @PutMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody(required = true)CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    @GetMapping("getPublishInfo/{courseId}")
    public Result getPublishInfo(@PathVariable(value = "courseId",required = true) String courseId){
        //返回添加之后课程id，为了后面添加大纲使用
        CoursePublishVo coursePublishVo = courseService.getPublishInfo(courseId);
        return Result.ok().data("coursePublishVo",coursePublishVo);
    }

}

