package com.lixuan.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lixuan.service.edu.client.VodClient;
import com.lixuan.service.edu.entity.Video;
import com.lixuan.service.edu.mapper.VideoMapper;
import com.lixuan.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Autowired
    private VodClient vodClient;

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
        Video video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideo(videoSourceId);
        }
        videoMapper.deleteById(id);
    }

    @Override
    public void deleteByCourseId(String courseId) {
        QueryWrapper<Video> query = Wrappers.<Video>query();
        query.eq("course_id",courseId);
        List<Video> list = videoMapper.selectList(query);
        ArrayList<String> videoSourceList = new ArrayList<>();
        for (Video video:list){
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoSourceList.add(videoSourceId);
            }
        }
        if (videoSourceList.size()>0){
            vodClient.deleteVideoList(videoSourceList);
        }
        videoMapper.delete(query);
    }
}
