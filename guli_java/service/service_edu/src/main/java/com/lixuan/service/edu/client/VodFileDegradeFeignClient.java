package com.lixuan.service.edu.client;

import com.lixuan.common.base.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频错误");
    }

    @Override
    public Result deleteVideoList(List<String> videoIdList) {
        return Result.error().message("批量删除视频错误");
    }
}
