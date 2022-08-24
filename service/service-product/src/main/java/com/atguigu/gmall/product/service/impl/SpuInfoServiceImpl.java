package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.mapper.SpuImageMapper;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import com.atguigu.gmall.product.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService{

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuImageMapper spuImageMapper;
    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    /**
     * 添加spu
     * @param spuInfo
     */
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //添加商品spuInfo信息
        if (spuInfo != null) spuInfoMapper.insert(spuInfo);
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //循环添加spu图片信息
        for (SpuImage spuImage : spuImageList){
            spuImage.setSpuId(spuInfo.getId());
            spuImageMapper.insert(spuImage);
        }
        //循环添加spu销售信息
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList){
            spuSaleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insert(spuSaleAttr);
        }
    }
}
