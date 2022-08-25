package com.atguigu.gmall.activity.service.impl;

import com.atguigu.gmall.activity.mapper.ActivityInfoMapper;
import com.atguigu.gmall.activity.service.ActivityInfoService;
import com.atguigu.gmall.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService{

    @Autowired
    ActivityInfoMapper activityInfoMapper;
    /**
     * 添加或修改活动信息
     * @param activityInfo
     * @return
     */
    @Override
    public void saveActivityInfo(ActivityInfo activityInfo) {
       if (activityInfo.getId() != null){
           activityInfoMapper.updateById(activityInfo);
       }else {
           activityInfo.setCreateTime(new Date());
           activityInfoMapper.insert(activityInfo);
       }
    }
}
