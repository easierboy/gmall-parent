package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.DateUtil;
import com.atguigu.gmall.product.config.minio.MinioAutoConfiguration;
import com.atguigu.gmall.product.config.minio.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {

    @Autowired
    MinioAutoConfiguration minioAutoConfiguration;
    @Autowired
    MinioProperties minioProperties;


    /**
     * 文件上传：以二进制流 请求体中
     * 如何接：  1 @RequestParam("file")MultipartFile file
     *         2 @RequestPart("file")MultipartFile file (推荐：@RequestPart专用于接收二进制流)
     * @return
     */
    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestPart("file")MultipartFile file){

        try {
            MinioClient minioClient = minioAutoConfiguration.getMinioClient();

            // 查询储存桶是否存在，如果不存在，则制作bucketName存储桶。
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called bucketName.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }else {
                System.out.println("储存桶" + minioProperties.getBucketName() + "已经存在。");
            }

            //添加日期文件夹
            String date = DateUtil.formatDate(new Date());
            //  定义一个文件的名称:名称不能重复！
            String fileName = date + "/" + System.currentTimeMillis() + UUID.randomUUID()+ file.getOriginalFilename();

            //方式二:文件上传uploadObject（只能用于普通文件File）
//            //调用工具类将MultipartFile 转File
//            File file1 = MultipartFileToFileUtils.multipartFileToFile(file);
//            //删除本地临时文件
//            MultipartFileToFileUtils.delteTempFile(file1);
//            // 将“/home/user/Photos/asiaphotos.zip”作为对象名称“asiaphotos-2015.zip”上传到存储桶
//            minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .filename(file1.getName())
//                            .build());
            //方式一:文件上传putObject
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                    .build());

            String url = minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
            return Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return Result.fail();
    }
}

//MultipartFile 转File工具类
class MultipartFileToFileUtils {

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
