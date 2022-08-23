package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo> implements BaseAttrInfoService{

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Override
    public List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long c1Id, Long c2Id, Long c3Id) {
        List<BaseAttrInfo> list = baseAttrInfoMapper.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);
        return list;
    }
}
