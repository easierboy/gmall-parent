package com.atguigu.starter.cache.service.impl;


import com.atguigu.starter.cache.constant.SysRedisConst;
import com.atguigu.starter.cache.service.CacheOpsService;
import com.atguigu.starter.cache.utils.Jsons;
import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 20:59
 */
@Service
public class CacheOpsServiceImpl implements CacheOpsService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public <T> T getCacheData(String cacheKey, Class<T> clz) {
        String s = redisTemplate.opsForValue().get(cacheKey);
        //引入null值缓存机制。
        if(SysRedisConst.NULL_VAL.equals(s)){
            return null;
        }
        return Jsons.toObj(s,clz);
    }

    @Override
    public Object getCacheData(String cacheKey, Type type) {
        String s = redisTemplate.opsForValue().get(cacheKey);

        //引入null值缓存机制。
        if(SysRedisConst.NULL_VAL.equals(s)){
            return null;
        }

        //获取TypeReference
        TypeReference<Object> typeReference = new TypeReference<Object>() {
            @Override
            public Type getType() {
                return type;//这个是方法的带泛型的返回值类型
            }
        };

        return Jsons.toObj(s,typeReference);
    }

    @Override
    public boolean bloomContains(Object obj) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return filter.contains(obj);
    }

    @Override
    public boolean bloomContains(String bloomName, Object bVal) {
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(bloomName);
        return filter.contains(bVal);
    }

    @Override
    public boolean tryLock(Long skuId) {
        //1、准备锁用的key    lock:sku:detail:50
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL+skuId;
        RLock lock = redissonClient.getLock(lockKey);
        //2、尝试加锁
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String lockName) {
        RLock rLock = redissonClient.getLock(lockName);
        return rLock.tryLock();
    }

    @Override
    public void saveData(String cacheKey, Object fromRpc) {
        if (fromRpc == null){
            redisTemplate.opsForValue().set(
                    cacheKey,
                    SysRedisConst.NULL_VAL,
                    SysRedisConst.NULL_VAL_TTL,
                    TimeUnit.SECONDS
            );
        }else {
            String s = Jsons.toStr(fromRpc);
            redisTemplate.opsForValue().set(
                    cacheKey,
                    s,
                    SysRedisConst.SKUDETAIL_TTL,
                    TimeUnit.SECONDS
            );
        }
    }

    @Override
    public void unlock(Long skuId) {
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL+skuId;
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
    }
}
