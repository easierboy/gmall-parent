package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SpuSaleAttrService extends IService<SpuSaleAttr>{


    /**
     * 根据spuId获取销售属性
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrListById(Long spuId);
}
