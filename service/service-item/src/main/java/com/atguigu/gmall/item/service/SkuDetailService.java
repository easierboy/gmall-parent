package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/26 23:08
 */
public interface SkuDetailService {
    /**
     * 获取商品详情
     * @param skuId
     * @return
     */
    SkuDetailTo getSkuDetail(Long skuId);
}
