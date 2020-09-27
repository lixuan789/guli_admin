package com.lixuan.service.edu.entity.subject;

import lombok.Data;

import java.util.List;

/**
 * 课程一级分类实体类
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children;//一个一级分类下有多个二级分类
}
