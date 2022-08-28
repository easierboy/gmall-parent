package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.ValueSkuJsonTo;
import com.atguigu.gmall.product.mapper.SkuSaleAttrValueMapper;
import com.atguigu.gmall.product.mapper.SpuSaleAttrMapper;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpuSaleAttrServiceImpl extends ServiceImpl<SpuSaleAttrMapper, SpuSaleAttr> implements SpuSaleAttrService{

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    /**
     * 根据spuId获取销售属性
     * @param spuId
     * @return
     */
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListById(Long spuId) {
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.getSpuSaleAttrListById(spuId);
        return spuSaleAttrList;
    }

    /**
     * 商品（sku）所属的SPU当时定义的所有销售属性名值组合（固定好顺序）。
     *      spu_sale_attr、spu_sale_attr_value
     *     并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
     * 查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
     * @param spuId
     * @param skuId
     * @return
     */
    @Override
    public List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId) {
        return spuSaleAttrMapper.getSaleAttrAndValueMarkSku(spuId, skuId);
    }


    /**
     *  商品（sku）的所有兄弟产品的销售属性名和值组合关系全部查出来，并封装成
     *  {"118|120": "50","119|121": 50} 这样的json字符串
     * @param spuId
     * @return
     */
    @Override
    public String getAllSkuSaleAttrValueJson(Long spuId) {
        List<ValueSkuJsonTo> valueSkuJsonTos = spuSaleAttrMapper.getAllSkuValueJson(spuId);
        // {"118|120":49,"119|121":50}
        // StreamAPI  lambda；
        Map<String,Long> map = new HashMap<>();
        for (ValueSkuJsonTo valueSkuJsonTo :valueSkuJsonTos){
            String valueJson = valueSkuJsonTo.getValueJson(); // 118|120
            Long skuId = valueSkuJsonTo.getSkuId(); // 49
            map.put(valueJson,skuId);
        }
        //fastjson  springboot: jackson
        String json = Jsons.toStr(map);

        return json;
    }
}
