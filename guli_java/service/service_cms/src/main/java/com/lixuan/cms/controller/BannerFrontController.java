package com.lixuan.cms.controller;

import com.lixuan.cms.entity.CrmBanner;
import com.lixuan.cms.service.CrmBannerService;
import com.lixuan.common.base.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/front/banner")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public Result index() {
        List<CrmBanner> list = bannerService.selectIndexList();
        return Result.ok().data("bannerList", list);
    }
}
