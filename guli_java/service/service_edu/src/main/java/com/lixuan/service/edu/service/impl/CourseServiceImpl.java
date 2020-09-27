package com.lixuan.service.edu.service.impl;

import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.CourseDescription;
import com.lixuan.service.edu.entity.vo.CourseInfoVo;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import com.lixuan.service.edu.mapper.CourseDescriptionMapper;
import com.lixuan.service.edu.mapper.CourseMapper;
import com.lixuan.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;
    /**
     * 添加课程的基本信息
     * @param courseInfoVo
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        boolean save = this.save(course);
        if (!save){//插入失败
            throw new RuntimeException("添加课程信息失败");
        }
        String courseId = course.getId();//通过主键进行级联
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseId);
        courseDescriptionMapper.insert(courseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        baseMapper.updateById(course);//更新课程
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionMapper.updateById(courseDescription);//更新简介
    }

    @Override
    public CoursePublishVo getPublishInfo(String courseId) {
        CoursePublishVo coursePublishVo=courseDescriptionMapper.getPublishInfo(courseId);
        return coursePublishVo;
    }
}
