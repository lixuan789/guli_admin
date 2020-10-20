package com.lixuan.service.edu.client;

import com.lixuan.common.base.result.Result;
import com.lixuan.service.edu.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    @PostMapping("/ucenter/member/getInfoUc/{id}")
    public Member getInfo(@PathVariable String id);
}
