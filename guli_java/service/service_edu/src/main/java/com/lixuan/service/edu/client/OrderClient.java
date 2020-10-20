package com.lixuan.service.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
@Component
public interface OrderClient {

    @GetMapping("/order/isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId, @PathVariable String courseId);
}
