package com.atguigu.gmall.product;

import io.minio.MinioClient;
import org.junit.jupiter.api.Test;


//@SpringBootTest
public class MinioTest {

    @Test
    public void uploadFile() throws Exception{

        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://192.168.6.188:9000")
                .credentials("admin","admin123456").build();

    }

}
