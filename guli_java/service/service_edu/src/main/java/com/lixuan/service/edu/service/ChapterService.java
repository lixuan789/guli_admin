package com.lixuan.service.edu.service;

import com.lixuan.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface ChapterService extends IService<Chapter> {

    List<Chapter> getAllChapter(String courseId);

    void addChapter(Chapter chapter);

    void updateChapter(Chapter chapter);

    void deleteChapter(String id);

    Chapter getChapter(String id);
}
