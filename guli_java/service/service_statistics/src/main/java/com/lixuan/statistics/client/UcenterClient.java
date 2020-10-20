package com.lixuan.statistics.client;

import com.lixuan.common.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    @GetMapping(value = "/ucenter/member/countRegister/{day}")
    public Result registerCount(@PathVariable String day);
}
