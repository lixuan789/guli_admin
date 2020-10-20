package com.lixuan.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lixuan.common.base.result.Result;
import com.lixuan.vod.service.VideoService;
import com.lixuan.vod.utils.ConstantPropertiesUtil;
import com.lixuan.vod.utils.VodUtil;
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

    @GetMapping("getPlayAuth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {

        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        //初始化
        DefaultAcsClient client = VodUtil.initVodClient(accessKeyId, accessKeySecret);

        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        //响应
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);

        //得到播放凭证
        String playAuth = response.getPlayAuth();

        //返回结果
        return Result.ok().message("获取凭证成功").data("playAuth", playAuth);
    }
}