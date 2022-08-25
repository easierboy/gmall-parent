package com.atguigu.gmall.activity.controller;

import com.atguigu.gmall.activity.service.ActivityInfoService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    /**
     * 添加活动信息
     * @param activityInfo
     * @return
     */
    @PostMapping("/activityInfo/save")
    public Result saveActivityInfo(@RequestBody ActivityInfo activityInfo){
        activityInfoService.saveActivityInfo(activityInfo);
        return Result.ok();
    }

    /**
     * 修改活动信息
     * @param activityInfo
     * @return
     */
    @PutMapping("/activityInfo/update")
    public Result updateActivityInfo(@RequestBody ActivityInfo activityInfo){
        activityInfoService.saveActivityInfo(activityInfo);
        return Result.ok();
    }

    /**
     * 根据id获取活动信息
     * @param id
     * @return
     */
    @GetMapping("/activityInfo/get/{id}")
    public Result getActivityInfoById(@PathVariable("id")Long id){
        ActivityInfo activityInfo = activityInfoService.getById(id);
        return Result.ok(activityInfo);
    }

    @DeleteMapping("/activityInfo/remove/{id}")
    public Result remove(@PathVariable("id")Long id){
        activityInfoService.removeById(id);
        return Result.ok();
    }

    @DeleteMapping("/activityInfo/batchRemove")
    public Result batchRemove(@RequestBody Long[] ids){
        for (Long id : ids){
            activityInfoService.removeById(id);
        }
        return Result.ok();
    }
}
