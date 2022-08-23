package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/product")
@RestController
public class BaseAttrController {

    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService baseAttrValueService;

    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result attrInfoList(@PathVariable("c1Id")Long c1Id,
                               @PathVariable("c2Id")Long c2Id,
                               @PathVariable("c3Id")Long c3Id){
        List<BaseAttrInfo> list = baseAttrInfoService.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);
        return Result.ok(list);
    }



    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(BaseAttrInfo baseAttrInfo){
        boolean b = baseAttrInfoService.save(baseAttrInfo);
        List<BaseAttrValue> list = baseAttrInfo.getAttrValueList();
        boolean b1 = baseAttrValueService.saveBatch(list);
        if (b && b1){
            return Result.ok().code(200).message("成功");
        }else {
            return Result.fail().code(20001).message("添加失败");
        }

    }
}
