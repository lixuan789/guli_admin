package com.lixuan.service.edu.controller.front;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.Teacher;
import com.lixuan.service.edu.entity.vo.TeacherQueryVo;
import com.lixuan.service.edu.service.CourseService;
import com.lixuan.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Api("讲师管理")
@RestController
@RequestMapping("/admin/teacher/front")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @ApiOperation("分页讲师列表")
    @PostMapping("list/{page}/{limit}")
    public Result listPageWeb(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit){
        Map<String, Object> map=teacherService.listPageWeb(page, limit);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public Result getById(@ApiParam(name = "id", value = "讲师ID", required = true)
                                        @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        //根据讲师id查询这个讲师的课程列表
        List<Course> courseList=courseService.selectByTeacherId(id);
        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }


}

