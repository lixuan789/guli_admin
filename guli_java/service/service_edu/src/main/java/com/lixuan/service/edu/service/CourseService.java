package com.lixuan.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lixuan.service.edu.entity.vo.CourseInfoVo;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import com.lixuan.service.edu.entity.vo.CourseQeryVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishInfo(String courseId);

    void publishCourse(String courseId);

    Page<Course> selectPage(Long page, Long limit, CourseQeryVo courseQeryVo);

    void deleteCourse(String courseId);
}
