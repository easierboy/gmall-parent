package com.atguigu.gmall.activity.service;

import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ActivityInfoService extends IService<ActivityInfo>{

    /**
     * 添加或修改活动信息
     * @param activityInfo
     * @return
     */
    void saveActivityInfo(ActivityInfo activityInfo);
}
