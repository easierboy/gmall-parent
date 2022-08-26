package com.atguigu.gmall.product.mapper;

import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/26 20:44
 */
public interface BaseCategory2Mapper extends BaseMapper<BaseCategory2> {
    /**
     * 查询所有分类并封装成树形菜单结构
     * @return
     */
    List<CategoryTreeTo> getAllCategoryWithTree();
}
