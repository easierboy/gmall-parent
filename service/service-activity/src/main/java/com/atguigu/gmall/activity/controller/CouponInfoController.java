package com.atguigu.gmall.activity.controller;

import com.atguigu.gmall.activity.service.CouponInfoService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.CouponInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lg
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/25 23:53
 */
@RestController
@RequestMapping("/admin/activity")
public class CouponInfoController {

    @Autowired
    CouponInfoService couponInfoService;

    @GetMapping("/couponInfo/{pn}/{ps}")
    public Result couponInfo(@PathVariable("pn")Long pn,
                             @PathVariable("ps")Long ps){
        Page<CouponInfo> page = couponInfoService.page(new Page<>(pn, ps));
        return Result.ok(page);
    }
}
