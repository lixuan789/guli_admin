package com.lixuan.service.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 课程最终发布信息实体类
 */
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
