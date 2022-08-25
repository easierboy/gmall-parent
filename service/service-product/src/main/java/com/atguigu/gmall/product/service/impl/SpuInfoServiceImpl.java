package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.SpuImage;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.product.SpuSaleAttrValue;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService{

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuImageService spuImageService;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    SpuSaleAttrValueService spuSaleAttrValueService;
    /**
     * 添加spu
     * @param spuInfo
     */
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {
        //添加商品spuInfo信息
        spuInfoMapper.insert(spuInfo);
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //循环回填spuId
        for (SpuImage spuImage : spuImageList){
            spuImage.setSpuId(spuInfo.getId());
        }
        //批量添加图片信息到数据库
        spuImageService.saveBatch(spuImageList);
        //循环回填spuId
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList){

            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            //循环回填spuId和SaleAttrName
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList){
                spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                spuSaleAttrValue.setSpuId(spuInfo.getId());
            }
            //批量添加销售属性值信息到数据库
            spuSaleAttrValueService.saveBatch(spuSaleAttrValueList);
            //循环回填spuId
            spuSaleAttr.setSpuId(spuInfo.getId());
        }
        //批量添加销售属性名信息到数据库
        spuSaleAttrService.saveBatch(spuSaleAttrList);
    }
}
