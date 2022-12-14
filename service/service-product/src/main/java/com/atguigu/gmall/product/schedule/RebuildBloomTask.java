package com.atguigu.gmall.product.schedule;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Author lg
 * @Description 重建布隆任务
 * @Version 1.0.0
 * @Date 2022/9/1 20:00
 */
@Service
public class RebuildBloomTask {

    @Autowired
    BloomOpsService bloomOpsService;

    @Autowired
    BloomDataQueryService bloomDataQueryService;

    @Scheduled(cron = "0 0 3 ? * 3")
    public void rebuild(){
        bloomOpsService.rebuildBloom(SysRedisConst.BLOOM_SKUID,bloomDataQueryService);
    }
}
