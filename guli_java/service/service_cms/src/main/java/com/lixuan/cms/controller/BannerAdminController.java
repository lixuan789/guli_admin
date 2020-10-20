package com.lixuan.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.cms.entity.CrmBanner;
import com.lixuan.cms.service.CrmBannerService;
import com.lixuan.common.base.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner后台控制器
 * </p>
 *
 * @author lixuan
 * @since 2020-10-09
 */
@RestController
@RequestMapping("/cms/banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    /**
     * 轮播图分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("getPage/{page}/{limit}")
    public Result index(@PathVariable(value = "page",required = true) Long page,
                        @PathVariable(value = "limit",required = true) Long limit){
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        bannerPage=crmBannerService.getPage(bannerPage);
        return Result.ok().data("list",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    /**
     * 根据id获取banner
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public Result getById(@PathVariable("id") String id){
        CrmBanner banner = crmBannerService.getById(id);
        return Result.ok().data("item",banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public Result save(@RequestBody CrmBanner banner) {
        crmBannerService.save(banner);
        return Result.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return Result.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return Result.ok();
    }
}

