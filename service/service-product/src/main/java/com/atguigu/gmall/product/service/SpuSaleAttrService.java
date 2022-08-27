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

    /**
     * 商品（sku）所属的SPU当时定义的所有销售属性名值组合（固定好顺序）。
     *   spu_sale_attr、spu_sale_attr_value
     *   并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
     *  查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
     * @param spuId
     * @param skuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId);
}
