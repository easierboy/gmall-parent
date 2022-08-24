package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/product")
@RestController
public class SpuController {

    @Autowired
    SpuInfoService spuInfoService;

    /**
     * 根据三级id获取spu分页列表
     * @return
     */
    @GetMapping("/{pn}/{ps}")
    public Result list(@PathVariable("pn")Integer pn,
                       @PathVariable("ps")Integer ps,
                       Long category3Id){
        Page<SpuInfo> page = new Page<>(pn,ps);
        QueryWrapper<SpuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category3_id",category3Id);
        Page<SpuInfo> pageResult = spuInfoService.page(page,queryWrapper);
        return Result.ok(pageResult);
    }

    /**
     * 添加spu
     * @param spuInfo
     */
    @PostMapping("/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo){
        spuInfoService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}
