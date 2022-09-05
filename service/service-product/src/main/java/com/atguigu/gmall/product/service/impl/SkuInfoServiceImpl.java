package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.config.RedissonConfig;
import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchAttr;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    SearchFeignClient searchFeignClient;
    @Autowired
    BaseTrademarkService baseTrademarkService;
    @Autowired
    SkuAttrValueService skuAttrValueService;
    /**
     * 添加sku
     * @param skuInfo
     * @return
     */
    @Transactional
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
        
        //2、给es中保存这个商品，商品就能被检索到了
        Goods goods = getGoodsBySkuId(skuId);
        searchFeignClient.saveGoods(goods);
    }

    private Goods getGoodsBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        Goods goods = new Goods();
        goods.setId(skuId);
        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setTitle(skuInfo.getSkuName());
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setCreateTime(new Date());
        goods.setTmId(skuInfo.getTmId());


        BaseTrademark trademark = baseTrademarkService.getById(skuInfo.getTmId());
        goods.setTmName(trademark.getTmName());
        goods.setTmLogoUrl(trademark.getLogoUrl());


        Long category3Id = skuInfo.getCategory3Id();
        CategoryViewTo view = baseCategory3Mapper.getCategoryView(category3Id);
        goods.setCategory1Id(view.getCategory1Id());
        goods.setCategory1Name(view.getCategory1Name());
        goods.setCategory2Id(view.getCategory2Id());
        goods.setCategory2Name(view.getCategory2Name());
        goods.setCategory3Id(view.getCategory3Id());
        goods.setCategory3Name(view.getCategory3Name());

        goods.setHotScore(0L); //TODO 热度分更新

        //查当前sku所有平台属性名和值
        List<SearchAttr> attrs = skuAttrValueService.getSkuAttrNameAndValue(skuId);
        goods.setAttrs(attrs);


        return goods;
    }

    /**
     * 下架商品
     * @param skuId
     */
    @Override
    public void cancelSale(Long skuId) {

        ///1、改数据库 sku_info 这个skuId的is_sale； 1上架  0下架
        skuInfoMapper.updateIsSale(skuId,0);

        //2、给es中删除这个商品
        searchFeignClient.deleteGoods(skuId);
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
