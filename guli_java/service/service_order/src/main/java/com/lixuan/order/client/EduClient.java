package com.lixuan.order.client;

import com.lixuan.common.base.result.Result;
import com.lixuan.order.entity.CourseWebVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu",fallback = EduClientImpl.class)
@Component
public interface EduClient {

    @GetMapping(value = "/admin/course/front/getCourseInfo/{courseId}")
    public CourseWebVo getCourseInfo(@PathVariable String courseId);
}
