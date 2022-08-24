package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {
    @Value("${minio.endpointUrl}")
    private String endpointUrl;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secreKey}")
    private String secreKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 文件上传：以二进制流 请求体中
     * 如何接：  1 @RequestParam("file")MultipartFile file
     *         2 @RequestPart("file")MultipartFile file (推荐：@RequestPart专用于接收二进制流)
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestPart("file")MultipartFile file){
        System.out.println(endpointUrl);
        System.out.println(accessKey);
        System.out.println(secreKey);

        try {
            //使用 MinIO 服务器游乐场、其访问密钥和密钥创建一个 minioClient
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpointUrl)
                            .credentials(accessKey, secreKey)
                            .build();

            // 查询储存桶是否存在，如果不存在，则制作bucketName存储桶。
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket called bucketName.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }else {
                System.out.println("储存桶" + bucketName + "已经存在。");
            }

            //  定义一个文件的名称:名称不能重复！
            String fileName = System.currentTimeMillis() + UUID.randomUUID().toString() + file.getOriginalFilename();

            // 将“/home/user/Photos/asiaphotos.zip”作为对象名称“asiaphotos-2015.zip”上传到存储桶
//            minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .filename(file.getOriginalFilename())
//                            .build());
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                    file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                    .build());

            String url = endpointUrl + "/" + bucketName + "/" + fileName;
            return Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return Result.fail();
    }
}
