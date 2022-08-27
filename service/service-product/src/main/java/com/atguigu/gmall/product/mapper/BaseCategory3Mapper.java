package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;

public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {
    /**
     * 根据三级分类id查询所有分类信息
     * @param category3Id
     * @return
     */
    CategoryViewTo getCategoryView(@Param("c3Id") Long category3Id);
}