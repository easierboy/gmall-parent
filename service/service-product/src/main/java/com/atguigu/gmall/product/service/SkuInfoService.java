package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SkuInfoService extends IService<SkuInfo>{


    /**
     * 添加sku
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);
}
