package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 19:43
 */
@Service
public class BloomDataQueryServiceImpl implements BloomDataQueryService{

    @Autowired
    SkuInfoService skuInfoService;

    @Override
    public List queryData() {
        return skuInfoService.findAllSkuId();
    }
}
