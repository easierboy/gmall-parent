package com.atguigu.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/3 20:37
 */
@EnableElasticsearchRepositories
@SpringCloudApplication
public class ElasticSearchMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchMainApplication.class,args);
    }
}
