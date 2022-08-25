package com.atguigu.gmall.activity.controller;

import com.atguigu.gmall.activity.service.ActivityInfoService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/activity")
public class ActivityController {

    @Autowired
    ActivityInfoService activityInfoService;

    /**
     * 分页查询活动列表
     * @param pn
     * @param ps
     * @return
     */
    @GetMapping("/activityInfo/{pn}/{ps}")
    public Result activityInfo(@PathVariable("pn")Long pn,
                               @PathVariable("ps")Long ps){
        Page<ActivityInfo> page = activityInfoService.page(new Page<>(pn, ps));
        return Result.ok(page);
    }
}
