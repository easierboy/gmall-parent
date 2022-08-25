package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SkuAttrValue;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SkuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService{

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SkuAttrValueService attrValueService;
    @Autowired
    SkuSaleAttrValueService saleAttrValueService;

    /**
     * 添加sku
     * @param skuInfo
     * @return
     */
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        skuInfoMapper.insert(skuInfo);
        List<SkuImage> imageList = skuInfo.getSkuImageList();
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuImage skuImage : imageList){
            skuImage.setSkuId(skuInfo.getId());
        }
        skuImageService.saveBatch(imageList);
        for (SkuAttrValue skuAttrValue : skuAttrValueList){
            skuAttrValue.setSkuId(skuInfo.getId());
        }
        attrValueService.saveBatch(skuAttrValueList);
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList){
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
        }
        saleAttrValueService.saveBatch(skuSaleAttrValueList);
    }

    /**
     * 上架商品
     * @param skuId
     */
    @Override
    public void onSale(Long skuId) {
        //1、改数据库 sku_info 这个skuId的is_sale； 1上架  0下架
        skuInfoMapper.updateIsSale(skuId,1);

        //TODO 2、从es中添加这个商品
    }

    /**
     * 下架商品
     * @param skuId
     */
    @Override
    public void cancelSale(Long skuId) {

        ///1、改数据库 sku_info 这个skuId的is_sale； 1上架  0下架
        skuInfoMapper.updateIsSale(skuId,0);

        //TODO 2、从es中删除这个商品
    }
}
