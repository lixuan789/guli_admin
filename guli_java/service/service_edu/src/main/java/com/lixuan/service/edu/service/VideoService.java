package com.lixuan.service.edu.service;

import com.lixuan.service.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
public interface VideoService extends IService<Video> {

    List<Video> getAllVideo();

    Video getVideo(String id);

    void addVideo(Video video);

    void updateVideo(Video video);

    void deleteVideo(String id);

    void deleteByCourseId(String courseId);
}
