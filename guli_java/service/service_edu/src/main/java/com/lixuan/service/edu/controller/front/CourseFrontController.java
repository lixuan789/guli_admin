package com.lixuan.service.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.common.base.result.Result;
import com.lixuan.common.base.util.JwtUtils;
import com.lixuan.service.edu.client.OrderClient;
import com.lixuan.service.edu.entity.Chapter;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.vo.*;
import com.lixuan.service.edu.service.ChapterService;
import com.lixuan.service.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@RestController
@RequestMapping("/admin/course/front")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    /**
     * 课程分页列表
     * @param page
     * @param limit
     * @param courseFrontQeryVo
     * @return
     */
    @ApiOperation("课程分页列表")
    @PostMapping("list/{page}/{limit}")
    public Result listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                           @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
                           @ApiParam("课程列表查询对象") @RequestBody(required = false) CourseFrontQeryVo courseFrontQeryVo){
        Page<Course> coursePage = new Page<>(page, limit);
        Map<String, Object> map=courseService.selectPageFront(coursePage,courseFrontQeryVo);
        return Result.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public Result getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);

        //查询当前课程的章节信息
        List<Chapter> chapterList = chapterService.getAllChapter(courseId);

        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);

        return Result.ok().data("course", courseWebVo).data("chapterList", chapterList).data("isbuy",buyCourse);
    }

    @GetMapping(value = "getCourseInfo/{courseId}")
    public CourseWebVo getCourseInfo(@PathVariable String courseId){
        return courseService.selectInfoWebById(courseId);
    }

}

