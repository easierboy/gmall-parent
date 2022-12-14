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
}
