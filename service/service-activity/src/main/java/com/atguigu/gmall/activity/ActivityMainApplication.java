package com.atguigu.gmall.activity;

import com.atguigu.gmall.common.config.Swagger2Config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;

@Import({Swagger2Config.class})
@SpringCloudApplication
@MapperScan("com.atguigu.gmall.activity.mapper")
public class ActivityMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityMainApplication.class,args);
    }
}
