package com.zzy.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzy.cms.entity.CrmBanner;
import com.zzy.cms.service.CrmBannerService;
import com.zzy.utils.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-09
 */
@RestController
@RequestMapping("/cms/bannerAdmin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "根据id获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "添加banner")
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    // 1.分页查询banner
    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{pageNo}/{pageSize}")
    public R pageBanner(
            @ApiParam(name = "pageNo", value = "当前页码", required = true)
            @PathVariable long pageNo,

            @ApiParam(name = "pageSize", value = "每页记录数", required = true)
            @PathVariable long pageSize) {

        Page<CrmBanner> pageBanner = new Page<>(pageNo, pageSize);
        bannerService.page(pageBanner, null);

        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }


}


