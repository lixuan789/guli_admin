package com.lixuan.service.edu.mapper;

import com.lixuan.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getPublishInfo(String courseId);
}
