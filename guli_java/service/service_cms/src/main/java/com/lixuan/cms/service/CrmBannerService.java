package com.lixuan.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-09
 */
public interface CrmBannerService extends IService<CrmBanner> {

    Page<CrmBanner> getPage(Page<CrmBanner> bannerPage);

    List<CrmBanner> selectIndexList();
}
