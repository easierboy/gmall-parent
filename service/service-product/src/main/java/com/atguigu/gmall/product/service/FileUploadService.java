package com.atguigu.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author lg
 * @Description
 * @Version 1.0.0
 * @Date 2022/8/25 22:51
 */
public interface FileUploadService {
    /**
     * 上传文件
     */
    String fileUpload(MultipartFile file);
}
