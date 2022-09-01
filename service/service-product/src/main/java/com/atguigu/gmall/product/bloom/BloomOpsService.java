package com.atguigu.gmall.product.bloom;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/1 19:40
 */
public interface BloomOpsService {

    /**
     * 重建指定布隆过滤器
     * @param bloomName
     * @param dataQueryService
     */
    void rebuildBloom(String bloomName,BloomDataQueryService dataQueryService);
}
