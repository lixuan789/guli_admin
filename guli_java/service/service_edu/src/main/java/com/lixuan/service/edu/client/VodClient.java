package com.lixuan.service.edu.client;

import com.lixuan.common.base.result.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    @DeleteMapping("/api/vod/video/deleteVideo/{videoId}")
    Result deleteVideo(@PathVariable(value = "videoId",required = true) String videoId);

    @DeleteMapping("/api/vod/video/deleteVideoList")
    Result deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList);
}
