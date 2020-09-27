package com.lixuan.service.edu.service;

import com.lixuan.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lixuan.service.edu.entity.vo.CourseInfoVo;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;

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
}
