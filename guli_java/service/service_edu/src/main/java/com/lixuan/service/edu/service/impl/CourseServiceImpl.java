package com.lixuan.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.service.edu.client.OssClient;
import com.lixuan.service.edu.entity.Course;
import com.lixuan.service.edu.entity.CourseDescription;
import com.lixuan.service.edu.entity.vo.*;
import com.lixuan.service.edu.mapper.ChapterMapper;
import com.lixuan.service.edu.mapper.CourseDescriptionMapper;
import com.lixuan.service.edu.mapper.CourseMapper;
import com.lixuan.service.edu.mapper.VideoMapper;
import com.lixuan.service.edu.service.ChapterService;
import com.lixuan.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lixuan.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private OssClient ossClient;

    @Autowired
    private CourseMapper courseMapper;

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
        CoursePublishVo coursePublishVo=baseMapper.getPublishInfo(courseId);
        return coursePublishVo;
    }

    @Override
    public void publishCourse(String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(Course.Normal);
        baseMapper.updateById(course);
    }

    @Override
    public Page<Course> selectPage(Long page, Long limit, CourseQeryVo courseQeryVo) {
        QueryWrapper<Course> query = Wrappers.<Course>query();
        Page<Course> temp=new Page<>(page,limit);
        query.orderByDesc("gmt_create");
        if (courseQeryVo==null){
            return baseMapper.selectPage(temp,query);
        }
        String title = courseQeryVo.getTitle();
        String teacherId = courseQeryVo.getTeacherId();
        String subjectId = courseQeryVo.getSubjectId();
        String subjectParentId = courseQeryVo.getSubjectParentId();
        if (!StringUtils.isEmpty(title)){
            query.likeRight("title",title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            query.eq("teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            query.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            query.eq("subject_parent_id",subjectParentId);
        }
        return baseMapper.selectPage(temp,query);
    }

    @Override
    public void deleteCourse(String courseId) {
        videoService.deleteByCourseId(courseId);//删除所有视频
        chapterService.deleteByCourseId(courseId);//删除所有章节
        courseDescriptionMapper.deleteById(courseId);//删除对应的简介

        //删除课程封面
        Course course = baseMapper.selectById(courseId);
        String filename = course.getCover();
        System.out.println("filename:"+filename);

        ossClient.deleteOssFile(filename);

        baseMapper.deleteById(courseId);//删除课程
    }

    @Override
    public List<Course> selectByTeacherId(String id) {
        QueryWrapper<Course> query = Wrappers.<Course>query();
        query.eq("teacher_id",id);
        //按照最后更新时间倒序排列
        query.orderByDesc("gmt_modified");
        List<Course> courseList = baseMapper.selectList(query);
        return courseList;
    }

    @Override
    public Map<String, Object> selectPageFront(Page<Course> coursePage, CourseFrontQeryVo courseFrontQeryVo) {
        QueryWrapper<Course> query = Wrappers.<Course>query();
        if (!StringUtils.isEmpty(courseFrontQeryVo.getSubjectParentId())) {
            query.eq("subject_parent_id", courseFrontQeryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontQeryVo.getSubjectId())) {
            query.eq("subject_id", courseFrontQeryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontQeryVo.getBuyCountSort())) {
            query.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontQeryVo.getGmtCreateSort())) {
            query.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontQeryVo.getPriceSort())) {
            query.orderByDesc("price");
        }

        baseMapper.selectPage(coursePage, query);

        List<Course> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String courseId) {
        this.updatePageViewCount(courseId);
        return courseMapper.selectInfoWebById(courseId);
    }

    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
    }
}
