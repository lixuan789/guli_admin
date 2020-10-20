package com.lixuan.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixuan.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lixuan.service.edu.entity.vo.TeacherQueryVo;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface TeacherService extends IService<Teacher> {
    IPage<Teacher> selectPage(Long page, Long limit, TeacherQueryVo teacherQueryVo);

    Map<String, Object> listPageWeb(Long page, Long limit);
}
