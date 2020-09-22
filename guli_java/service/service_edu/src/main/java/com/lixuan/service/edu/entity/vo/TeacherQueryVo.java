package com.lixuan.service.edu.entity.vo;

import lombok.Data;

/**
 * 讲师查询类
 * @author LiXxuan
 * @date 2020/9/14 15:42
 */
@Data
public class TeacherQueryVo {
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer level;
    private String joinDateBegin;
    private String joinDateEnd;
}
