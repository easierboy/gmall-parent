package com.atguigu.gmall.model.to;

import lombok.Data;

/**
 * @Author lg
 * @Description 商品所属的分类信息
 * @Version 1.0.0
 * @Date 2022/8/26 22:56
 */
@Data
public class CategoryViewTo {
    private Long category1Id;
    private String category1Name;
    private Long category2Id;
    private String category2Name;
    private Long category3Id;
    private String category3Name;
}
