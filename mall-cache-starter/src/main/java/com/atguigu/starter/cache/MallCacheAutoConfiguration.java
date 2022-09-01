package com.atguigu.starter.cache;


import com.atguigu.starter.cache.aspect.CacheAspect;
import com.atguigu.starter.cache.service.CacheOpsService;
import com.atguigu.starter.cache.service.impl.CacheOpsServiceImpl;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StringUtils;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 21:24
 */
@EnableAspectJAutoProxy
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Configuration
public class MallCacheAutoConfiguration {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public CacheOpsService getCacheOpsService(){
        return  new CacheOpsServiceImpl();
    }

    @Bean
    public CacheAspect cacheAspect(){
        return new CacheAspect();
    }

    @Bean
    public RedissonClient redissonSingle() {
        //1、创建一个配置
        Config config = new Config();
        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        String password = redisProperties.getPassword();

        if(StringUtils.isEmpty(host)){
            throw new RuntimeException("host is  empty");
        }
        SingleServerConfig serverConfig = config.useSingleServer()
                //redis://127.0.0.1:7181
                .setAddress("redis://" + host + ":" + port);
        if(!StringUtils.isEmpty(password)) {
            serverConfig.setPassword(password);
        }
        return Redisson.create(config);
    }
}
