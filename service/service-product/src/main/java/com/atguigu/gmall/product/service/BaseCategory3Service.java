package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.BaseCategory3;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseCategory3Service extends IService<BaseCategory3>{


    List<BaseCategory3> getCategory2Child(Long c2Id);
}
