package com.atguigu.gmall.product.service;


import com.atguigu.gmall.model.product.BaseCategory3;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseCategory3Service extends IService<BaseCategory3>{


    List<BaseCategory3> getCategory2Child(Long c2Id);

    /**
     * 根据三级分类id查询1级和二级分类
     * @param c3Id
     * @return
     */
    CategoryViewTo getCategoryView(Long c3Id);
}
