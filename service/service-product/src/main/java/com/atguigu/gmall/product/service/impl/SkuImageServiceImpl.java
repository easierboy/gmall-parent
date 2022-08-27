package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.product.mapper.SkuImageMapper;
import com.atguigu.gmall.product.service.SkuImageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuImageServiceImpl extends ServiceImpl<SkuImageMapper, SkuImage> implements SkuImageService{

    @Autowired
    SkuImageMapper skuImageMapper;
    @Override
    public List<SkuImage> getSkuImage(Long skuId) {
        QueryWrapper<SkuImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sku_id",skuId);
        List<SkuImage> list = skuImageMapper.selectList(queryWrapper);
        return list;
    }
}
