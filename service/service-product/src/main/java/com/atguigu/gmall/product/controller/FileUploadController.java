package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {

    /**
     * 文件上传：以二进制流 请求体中
     * 如何接：  1 @RequestParam("file")MultipartFile file
     *         2 @RequestPart("file")MultipartFile file (推荐：@RequestPart专用于接收二进制流)
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestPart("file")MultipartFile file){


        return Result.ok();
    }

}
