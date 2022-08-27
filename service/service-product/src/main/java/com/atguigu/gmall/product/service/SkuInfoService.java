package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SkuInfoService extends IService<SkuInfo>{


    /**
     * 添加sku
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * 上架商品
     * @param skuId
     */
    void onSale(Long skuId);

    /**
     * 下架商品
     * @param skuId
     */
    void cancelSale(Long skuId);

    /**
     * 查询商品详情(前台展示)
     * @param skuId
     * @return
     */
    SkuDetailTo getSkuDetail(Long skuId);
}
