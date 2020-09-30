package com.lixuan.vod.controller;


import com.lixuan.common.base.result.Result;
import com.lixuan.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="阿里云视频点播微服务")
@CrossOrigin //跨域
@RestController
@RequestMapping("/api/vod/video")
public class VodController {

    @Autowired
    private VideoService videoService;

    /**
     * 上传视频
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return Result.ok().message("视频上传成功").data("videoId", videoId);
    }

    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable(value = "videoId",required = true) String videoId){
        videoService.deleteVideo(videoId);
        return Result.ok();
    }

    @DeleteMapping("deleteVideoList")
    public Result deleteVideoList(@ApiParam(name = "videoIdList", value = "云端视频id", required = true)
                                      @RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeVideoList(videoIdList);
        return Result.ok();
    }
}