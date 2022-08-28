package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3> implements BaseCategory3Service{

    @Autowired
    BaseCategory3Mapper baseCategory3Mapper;

    @Override
    public List<BaseCategory3> getCategory2Child(Long c2Id) {
        QueryWrapper<BaseCategory3> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category2_id",c2Id);
        List<BaseCategory3> list = baseCategory3Mapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 根据三级分类id查询1级和二级分类
     * @param c3Id
     * @return
     */
    @Override
    public CategoryViewTo getCategoryView(Long c3Id) {
        return baseCategory3Mapper.getCategoryView(c3Id);
    }
}
