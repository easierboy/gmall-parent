package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author lg
 * @Description 分类有关的API 所有的远程调用都是内部接口：命名规范：/api/inner/rpc/模块名/路径
 * @Version 1.0.0
 * @Date 2022/8/26 20:03
 */
@Api(tags = "三级分类的RPC接口")
@RequestMapping("/api/inner/rpc/product")
@RestController
public class CategoryApiController {


    @Autowired
    BaseCategory2Service baseCategory2Service;
    /**
     * 查询所有分类并封装成树形菜单结构
     * @return
     */
    @ApiOperation("三级分类树形结构查询")
    @GetMapping("/category/tree")
    public Result getAllCategoryWithTree(){

        List<CategoryTreeTo> categoryTreeTos = baseCategory2Service.getAllCategoryWithTree();

        return Result.ok(categoryTreeTos);
    }
}
