package com.lixuan.order.client;

import com.lixuan.order.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {
    @PostMapping("/ucenter/member/getInfoUc/{id}")
    public Member getInfo(@PathVariable String id);


}
