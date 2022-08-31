package com.atguigu.gmall.activity.controller;

import com.atguigu.gmall.activity.service.CouponInfoService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.CouponInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

import static java.time.Instant.now;

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

    /**
     * 获取优惠券分页列表
     * @param pn
     * @param ps
     * @return
     */
    @GetMapping("/couponInfo/{pn}/{ps}")
    public Result couponInfo(@PathVariable("pn")Long pn,
                             @PathVariable("ps")Long ps){
        Page<CouponInfo> page = couponInfoService.page(new Page<>(pn, ps));
        return Result.ok(page);
    }

    /**
     * 添加优惠券
     * @param couponInfo
     * @return
     */
    @PostMapping("/couponInfo/save")
    public Result saveCouponInfo(@RequestBody CouponInfo couponInfo){
        Date date = Date.from(now());
        couponInfo.setCreateTime(date);
        couponInfoService.save(couponInfo);
        return Result.ok();
    }

    /**
     * 修改优惠券
     * @param couponInfo
     * @return
     */
    @PutMapping("/couponInfo/update")
    public Result updateCouponInfo(@RequestBody CouponInfo couponInfo){
        couponInfoService.updateById(couponInfo);
        return Result.ok();
    }

    /**
     * 根据id获取优惠券详细信息
     * @param couId
     * @return
     */
    @GetMapping("/couponInfo/get/{couId}")
    public Result getCouponInfoById(@PathVariable("couId")Long couId){
        CouponInfo couponInfo = couponInfoService.getById(couId);
        return Result.ok(couponInfo);
    }


    /**
     * 根据id获删除优惠券
     * @param couId
     * @return
     */
    @DeleteMapping("/couponInfo/remove/{couId}")
    public Result deleteCouponInfoById(@PathVariable("couId")Long couId){
        couponInfoService.removeById(couId);
        return Result.ok();
    }

    /**
     * 批量删除
     * @param couIds
     * @return
     */
    @DeleteMapping("/couponInfo/batchRemove")
    public Result batchRemove(@RequestBody Long[] couIds){
        ArrayList<Long> list = new ArrayList<>();
        for (Long couId : couIds){
            list.add(couId);
        }
        couponInfoService.removeByIds(list);
        return Result.ok();
    }
}
