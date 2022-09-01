package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lg
 * @Description 布隆重建服务
 * @Version 1.0.0
 * @Date 2022/9/1 19:43
 */
@Slf4j
@Service
public class BloomOpsServiceImpl implements BloomOpsService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SkuInfoService skuInfoService;

    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService) {
        log.info("................布隆重建开始..............");
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomName);

        //1、先准备一个新的布隆过滤器。所有东西都初始化好
        String newBoolName = bloomName + "-new";
        RBloomFilter<Object> newBloomFilter = redissonClient.getBloomFilter(newBoolName);
        //2、拿到所有商品id
        List list = dataQueryService.queryData();//动态决定数据
        //3、初始化新的布隆
        newBloomFilter.tryInit(500000,0.00001);

        for (Object obj : list){
            newBloomFilter.add(obj);
        }
        //4、新布隆准备就绪
        // ob  bb  nb
        //5、两个交换；nb 要变成 ob。 大数据量的删除会导致redis卡死
        //最极致的做法：lua。 自己尝试写一下这lua脚本
        bloomFilter.rename("old_BloomFilter");
        newBloomFilter.rename(bloomName);

        //6、删除老布隆，和中间交换层
        bloomFilter.deleteAsync();
        redissonClient.getBloomFilter("old_BloomFilter").deleteAsync();
    }
}
