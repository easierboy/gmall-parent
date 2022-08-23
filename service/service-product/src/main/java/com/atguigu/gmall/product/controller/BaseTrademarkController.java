package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/product")
@RestController
public class BaseTrademarkController {

    @Autowired
    BaseTrademarkService baseTrademarkService;

    /**
     * 分页查询品牌信息
     * @param pn
     * @param size
     * @return
     */
    @GetMapping("/baseTrademark/{pn}/{size}")
    public Result baseTrademark(@PathVariable("pn")Integer pn,
                                @PathVariable("size")Integer size){
        Page<BaseTrademark> page = new Page<>(pn,size);

        Page<BaseTrademark> pageResult = baseTrademarkService.page(page);

        return Result.ok(pageResult);
    }

    /**
     * 根据品牌id获取品牌信息
     * @param id
     * @return
     */
    @GetMapping("/baseTrademark/get/{id}")
    public Result getBaseTrademark(@PathVariable("id")Long id){
        BaseTrademark trademark = baseTrademarkService.getById(id);
        return Result.ok(trademark);
    }

    /**
     * 修改品牌
     * @param trademark
     * @return
     */
    @PutMapping("/baseTrademark/update")
    public Result updateTrademark(@RequestBody BaseTrademark trademark){
        baseTrademarkService.updateById(trademark);
        return Result.ok();
    }

    /**
     * 保存品牌
     * @param trademark
     * @return
     */
    @PostMapping("/baseTrademark/save")
    public Result saveBaseTrademark(@RequestBody BaseTrademark trademark){
        baseTrademarkService.save(trademark);
        return Result.ok();
    }

    /**
     * 根据id删除品牌
     * @param tId
     * @return
     */
    @DeleteMapping("/baseTrademark/remove/{tId}")
    public Result deleteBaseTrademark(@PathVariable("tId")Long tId){
        baseTrademarkService.removeById(tId);
        return Result.ok();
    }
}
