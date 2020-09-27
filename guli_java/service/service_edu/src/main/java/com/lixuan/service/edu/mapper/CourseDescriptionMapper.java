package com.lixuan.service.edu.mapper;

import com.lixuan.service.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lixuan.service.edu.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程简介 Mapper 接口
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Repository
public interface CourseDescriptionMapper extends BaseMapper<CourseDescription> {

    CoursePublishVo getPublishInfo(String courseId);
}
