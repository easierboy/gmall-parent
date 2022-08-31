package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.config.RedissonConfig;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService{

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuAttrValueService attrValueService;
    @Autowired
    SkuSaleAttrValueService saleAttrValueService;
    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    RedissonClient redissonClient;

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

        //将skuId添加到布隆过滤器中
        redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID).add(skuInfo.getId());
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

    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {

        SkuDetailTo skuDetailTo = new SkuDetailTo();
        //商品的基本信息
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        //查询商品图片
        List<SkuImage> imageList = skuImageService.getSkuImage(skuId);
        skuInfo.setSkuImageList(imageList);
        skuDetailTo.setSkuInfo(skuInfo);

        //查询实时价格
        BigDecimal price = skuInfoMapper.selectPriceBySkuId(skuId);
        skuDetailTo.setPrice(price);
        //当前sku所属的分类信息
        CategoryViewTo categoryViewTo = baseCategory3Mapper.getCategoryView(skuInfo.getCategory3Id());
        skuDetailTo.setCategoryView(categoryViewTo);

        //商品（sku）所属的SPU当时定义的所有销售属性名值组合（固定好顺序）。
        //          spu_sale_attr、spu_sale_attr_value
        //          并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
        //查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService
                .getSaleAttrAndValueMarkSku(skuInfo.getSpuId(),skuId);
        skuDetailTo.setSpuSaleAttrList(saleAttrList);

        //  商品（sku）的所有兄弟产品的销售属性名和值组合关系全部查出来，并封装成
        //  {"118|120": "50","119|121": 50} 这样的json字符串
        String valueJson = spuSaleAttrService.getAllSkuSaleAttrValueJson(skuInfo.getSpuId());
        skuDetailTo.setValuesSkuJson(valueJson);

        return skuDetailTo;
    }

    @Override
    public SkuInfo getDetailSkuInfo(Long skuId) {
        return skuInfoMapper.selectById(skuId);
    }

    @Override
    public List<SkuImage> getDetailSkuImages(Long skuId) {
        return skuImageService.getSkuImage(skuId);
    }

    @Override
    public BigDecimal get1010Price(Long skuId) {
        return skuInfoMapper.selectPriceBySkuId(skuId);
    }

    @Override
    public List<Long> findAllSkuId() {
        return skuInfoMapper.findAllSkuId();
    }
}
