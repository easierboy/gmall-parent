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
}