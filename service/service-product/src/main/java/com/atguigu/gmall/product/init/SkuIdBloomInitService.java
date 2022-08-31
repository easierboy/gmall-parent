package com.atguigu.gmall.product.init;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.product.service.SkuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author lg
 * @Description 容器启动成功以后，连上数据库，查到所有商品id。在布隆里面进行占位
 * @Version 1.0.0
 * @Date 2022/8/31 17:21
 */
@Slf4j
@Service
public class SkuIdBloomInitService {
    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 项目一启动就运行
     */
    @PostConstruct  //当前组件对象创建成功以后
    public void initSkuBloom(){
        log.info("布隆初始化中。。。。。。。。。。。。。。");
        //1、查询出所有的skuId
        List<Long> skuIds  = skuInfoService.findAllSkuId();

        //2、把所有的id初始化到布隆过滤器中
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        //3、初始化布隆过滤器
        //long expectedInsertions, 期望插入的数据量
        //double falseProbability  误判率
        boolean exists = filter.isExists();
        if (!exists){
            //布隆过滤器未初始化过，尝试初始化
            filter.tryInit(5000000,0.00001);
        }
        for (Long skuId : skuIds){
            filter.add(skuId);
        }
        log.info("布隆初始化完成....，总计添加了 {} 条数据",skuIds.size());
    }
}
