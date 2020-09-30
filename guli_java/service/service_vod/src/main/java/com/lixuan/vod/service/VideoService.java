package com.lixuan.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    void removeVideoList(List<String> videoList);
}
