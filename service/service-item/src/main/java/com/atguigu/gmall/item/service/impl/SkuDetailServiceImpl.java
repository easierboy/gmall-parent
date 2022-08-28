package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.annotation.EnableThreadPool;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.feign.SkuDetailFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/26 23:08
 */
@Service
@EnableThreadPool
public class SkuDetailServiceImpl implements SkuDetailService {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    @Autowired
    ThreadPoolExecutor executor;


    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {

        SkuDetailTo skuDetailTo = new SkuDetailTo();

        //查询sku的基本信息
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            skuDetailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, executor);

        //查询商品图片
        //2、查商品图片信息  1s
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
            skuInfo.setSkuImageList(skuImages.getData());
        }, executor);

        //查询实时价格
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() ->{
            BigDecimal price = skuDetailFeignClient.getSku1010Price(skuId).getData();
            skuDetailTo.setPrice(price);
        },executor);

        //当前sku所属的分类信息
        CompletableFuture<Void> categoryViewToFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            CategoryViewTo categoryViewTo = skuDetailFeignClient.getCategoryView(skuInfo.getCategory3Id()).getData();
            skuDetailTo.setCategoryView(categoryViewTo);
        },executor);


        //商品（sku）所属的SPU当时定义的所有销售属性名值组合（固定好顺序）。
        //          spu_sale_attr、spu_sale_attr_value
        //          并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
        //查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
        CompletableFuture<Void> spuSaleAttrListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            List<SpuSaleAttr> spuSaleAttrList = skuDetailFeignClient.getSkuSaleattrvalues(skuId, skuInfo.getSpuId()).getData();
            skuDetailTo.setSpuSaleAttrList(spuSaleAttrList);
        },executor);


        //  商品（sku）的所有兄弟产品的销售属性名和值组合关系全部查出来，并封装成
        //  {"118|120": "50","119|121": 50} 这样的json字符串
        CompletableFuture<Void> valueJsonFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            String s = skuDetailFeignClient.getSKuValueJson(skuInfo.getSpuId()).getData();
            skuDetailTo.setValuesSkuJson(s);
        },executor);

        CompletableFuture.allOf(skuInfoFuture,valueJsonFuture,spuSaleAttrListFuture,imageFuture,categoryViewToFuture
                        ,priceFuture).join();
        return skuDetailTo;
    }
}
