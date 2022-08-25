package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface SkuInfoMapper extends BaseMapper<SkuInfo> {
    /**
     * 修改商品销售状态
     *
     * @param skuId
     * @param i
     */
    void updateIsSale(@Param("skuId") Long skuId, @Param("i") int i);
}