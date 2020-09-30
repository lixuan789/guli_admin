package com.lixuan.service.edu.client;

import com.lixuan.common.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-oss")
@Component
public interface OssClient {

    @DeleteMapping("/api/oss/detele")
    Result deleteOssFile(@RequestParam("filename") String filename);
}
