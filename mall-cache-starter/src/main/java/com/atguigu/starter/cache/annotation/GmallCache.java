package com.atguigu.starter.cache.annotation;

import java.lang.annotation.*;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 21:16
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GmallCache {

    String cacheKey() default ""; //就是cacheKey

    String bloomName() default "";//如果指定了布隆过滤器的名字，就用

    String bloomValue() default "";//指定布隆过滤器如果需要判定的话，用什么表达式计算出的值进行判定

    String lockName() default ""; //传入精确锁就用精确的，否则用全局默认的

    long ttl() default 60*30l; //缓存过期时间
}
