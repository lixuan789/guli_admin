package com.lixuan.service.edu.service.impl;

import com.lixuan.service.edu.entity.Video;
import com.lixuan.service.edu.mapper.VideoMapper;
import com.lixuan.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-09-14
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> getAllVideo() {
        List<Video> list = videoMapper.selectList(null);
        return list;
    }

    @Override
    public Video getVideo(String id) {
        Video video = videoMapper.selectById(id);
        return video;
    }

    @Override
    public void addVideo(Video video) {
        videoMapper.insert(video);
    }

    @Override
    public void updateVideo(Video video) {
        videoMapper.updateById(video);
    }

    @Override
    public void deleteVideo(String id) {
        videoMapper.deleteById(id);
    }
}
