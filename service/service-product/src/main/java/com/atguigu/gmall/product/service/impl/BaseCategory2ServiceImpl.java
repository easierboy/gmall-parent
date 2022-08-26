package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.mapper.BaseCategory2Mapper;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BaseCategory2ServiceImpl  extends ServiceImpl<BaseCategory2Mapper, BaseCategory2> implements BaseCategory2Service{

    @Autowired
    BaseCategory2Mapper baseCategory2Mapper;

    @Override
    public List<BaseCategory2> getCategory1Child(Long c1Id) {
        QueryWrapper<BaseCategory2> queryWrapper = new QueryWrapper();
        queryWrapper.eq("category1_id",c1Id);
        List<BaseCategory2> list = baseCategory2Mapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 查询所有分类并封装成树形菜单结构
     * @return
     */
    @Override
    public List<CategoryTreeTo> getAllCategoryWithTree() {
        List<CategoryTreeTo>  list = baseCategory2Mapper.getAllCategoryWithTree();
        return list;
    }
}
