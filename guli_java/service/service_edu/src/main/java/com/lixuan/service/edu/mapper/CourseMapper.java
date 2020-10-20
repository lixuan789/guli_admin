package com.lixuan.service.edu.mapper;

import com.lixuan.service.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import com.lixuan.service.edu.entity.vo.CourseWebVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getPublishInfo(String courseId);

    CourseWebVo selectInfoWebById(String courseId);
}
