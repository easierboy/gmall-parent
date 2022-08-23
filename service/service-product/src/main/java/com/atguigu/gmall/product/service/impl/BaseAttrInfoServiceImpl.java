package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo> implements BaseAttrInfoService{

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    /**
     * 查询制定分类下的所有属性
     * @param c1Id
     * @param c2Id
     * @param c3Id
     * @return
     */
    @Override
    public List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long c1Id, Long c2Id, Long c3Id) {
        List<BaseAttrInfo> list = baseAttrInfoMapper.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);
        return list;
    }

    /**
     * 保存属性
     * @param baseAttrInfo
     */
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        List<BaseAttrValue> list = baseAttrInfo.getAttrValueList();

        if (baseAttrInfo.getId() != null){
            //1、修改属性名
            baseAttrInfoMapper.updateById(baseAttrInfo);
            //删除
            List<BaseAttrValue> valueList = baseAttrInfo.getAttrValueList();
            ArrayList<Long> vList = new ArrayList<>();
            for (BaseAttrValue value : valueList){
                Long id = value.getId();
                if (id != null){
                    vList.add(id);
                }
            }
            if (vList.size()>0){
                QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("attr_id",baseAttrInfo.getId());
                queryWrapper.notIn("id",vList);
                baseAttrValueMapper.delete(queryWrapper);
            }else {
                QueryWrapper<BaseAttrValue> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("attr_id",baseAttrInfo.getId());
                baseAttrValueMapper.delete(queryWrapper);
            }
            //2、修改属性值（需要判断！！！！增删改操作）
            for (BaseAttrValue value : list){
                if (value.getId() == null) {
                    value.setAttrId(baseAttrInfo.getId());
                    baseAttrValueMapper.insert(value);
                }//增
                if(value.getId() != null) {
                    baseAttrValueMapper.updateById(value);//改
                }
            }
        }else {
            //1、保存属性名
            baseAttrInfoMapper.insert(baseAttrInfo);
            //获取保存之后的属性名自增id
            Long id = baseAttrInfo.getId();
            //2、保存属性值
            for (BaseAttrValue value : list){
                value.setAttrId(id);
                baseAttrValueMapper.insert(value);
            }
        }
    }
}
