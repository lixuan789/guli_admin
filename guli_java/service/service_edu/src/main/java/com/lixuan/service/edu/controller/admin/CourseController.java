package com.lixuan.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.Teacher;
import com.lixuan.service.edu.entity.vo.CourseInfoVo;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import com.lixuan.service.edu.entity.vo.CourseQeryVo;
import com.lixuan.service.edu.entity.vo.TeacherQueryVo;
import com.lixuan.service.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 获取发布信息
     * @param courseId
     * @return
     */
    @GetMapping("getPublishInfo/{courseId}")
    public Result getPublishInfo(@PathVariable(value = "courseId",required = true) String courseId){
        CoursePublishVo coursePublishVo = courseService.getPublishInfo(courseId);
        return Result.ok().data("coursePublishVo",coursePublishVo);
    }

    /**
     * 发布课程
     * @param courseId
     * @return
     */
    @PutMapping("publishCourse/{courseId}")
    public Result publishCourse(@PathVariable(value = "courseId",required = true) String courseId){
        courseService.publishCourse(courseId);
        return Result.ok();
    }

    /**
     * 课程分页列表
     * @param page
     * @param limit
     * @param courseQeryVo
     * @return
     */
    @ApiOperation("课程分页列表")
    @PostMapping("list/{page}/{limit}")
    public Result listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                           @ApiParam("课程列表查询对象") @RequestBody(required = false) CourseQeryVo courseQeryVo){
        Page<Course> res=courseService.selectPage(page,limit,courseQeryVo);
        List<Course> list = res.getRecords();
        long total = res.getTotal();
        return Result.ok().data("total",total).data("rows",list);
    }

    /**
     * 删除课程:视频、章节、课程、简介一起删除
     * @param courseId
     * @return
     */
    @DeleteMapping("deleteCourse/{courseId}")
    public Result deleteCourse(@PathVariable(value = "courseId",required = true) String courseId){
        courseService.deleteCourse(courseId);
        return Result.ok();
    }

}

