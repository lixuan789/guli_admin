package com.lixuan.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lixuan.cms.entity.CrmBanner;
import com.lixuan.cms.mapper.CrmBannerMapper;
import com.lixuan.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lixuan
 * @since 2020-10-09
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private CrmBannerMapper crmBannerMapper;
    @Override
    public Page<CrmBanner> getPage(Page<CrmBanner> bannerPage) {
        Page<CrmBanner> crmBannerPage = crmBannerMapper.selectPage(bannerPage, null);
        return crmBannerPage;
    }

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> list = crmBannerMapper.selectList(null);
        return list;
    }
}
