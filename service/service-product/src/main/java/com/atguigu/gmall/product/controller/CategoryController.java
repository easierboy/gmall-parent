package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/product")
@RestController
public class CategoryController {

    @Autowired
    BaseCategory1Service baseCategory1Service;

    @Autowired
    BaseCategory2Service baseCategory2Service;

    @Autowired
    BaseCategory3Service baseCategory3Service;

    @Autowired
    BaseAttrInfoService baseAttrInfoService;

    @Autowired
    BaseAttrValueService baseAttrValueService;
    /**
     * 获取所有的一级分类
     * @return
     */
    @GetMapping("/getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> list = baseCategory1Service.list();
        return Result.ok(list);
    }

    /**
     * 查询1级分类下的所有二级分类
     * @param c1Id
     * @return
     */
    @GetMapping("/getCategory2/{c1Id}")
    public Result getCategory2(@PathVariable("c1Id")Long c1Id){
        List<BaseCategory2> list = baseCategory2Service.getCategory1Child(c1Id);
        return Result.ok(list);
    }

    @GetMapping("/getCategory3/{c2Id}")
    public Result getCategory3(@PathVariable("c2Id")Long c2Id){
        List<BaseCategory3> list = baseCategory3Service.getCategory2Child(c2Id);
        return Result.ok(list);
    }

    @GetMapping("/attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    public Result attrInfoList(@PathVariable("c1Id")Long c1Id,
                               @PathVariable("c2Id")Long c2Id,
                               @PathVariable("c3Id")Long c3Id){
        List<BaseAttrInfo> list = baseAttrInfoService.attrInfoList(c1Id,c2Id,c3Id);
        return Result.ok(list);
    }

    @PostMapping("/saveAttrInfo")
    public Result<Object> saveAttrInfo(BaseAttrInfo baseAttrInfo){
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
