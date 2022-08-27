package com.atguigu.gmall.product.mapper;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    /**
     * getSpuSaleAttrListById
     * @param spuId
     * @return
     */
    List<SpuSaleAttr> getSpuSaleAttrListById(@Param("spuId") Long spuId);

    /**
     *  商品（sku）所属的SPU当时定义的所有销售属性名值组合（固定好顺序）。
     *       spu_sale_attr、spu_sale_attr_value
     *       并标识出当前sku到底spu的那种组合，页面要有高亮框 sku_sale_attr_value
     *      查询当前sku对应的spu定义的所有销售属性名和值（固定好顺序）并且标记好当前sku属于哪一种组合
     * @param spuId
     * @param skuId
     * @return
     */
    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(@Param("spuId") Long spuId,@Param("skuId") Long skuId);
}