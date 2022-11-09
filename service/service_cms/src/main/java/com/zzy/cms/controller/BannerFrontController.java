package com.zzy.cms.controller;


import com.zzy.cms.entity.CrmBanner;
import com.zzy.cms.service.CrmBannerService;
import com.zzy.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页banner表 前端控制器
 *
 * @author zzy
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/cms/bannerFront")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    //查询所有的banner
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list=bannerService.selectAllBanner();
        return R.ok().data("list",list);
    }
}

