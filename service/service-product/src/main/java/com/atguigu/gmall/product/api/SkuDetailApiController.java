package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/8/26 23:11
 */
@RequestMapping("/api/inner/rpc/product")
@RestController
public class SkuDetailApiController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/skudetail/{skuId}")
    Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId){

        SkuDetailTo skuDetailTo = skuInfoService.getSkuDetail(skuId);
        return Result.ok(skuDetailTo);
    }
}
