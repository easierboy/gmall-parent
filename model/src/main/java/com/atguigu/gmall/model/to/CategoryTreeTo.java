package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * @Author lg
 * @Description 三级分类树形结构；
 *              支持无限层级；
 *              当前项目只有三级
 * @Version 1.0.0
 * @Date 2022/8/26 19:59
 */
@Data
public class CategoryTreeTo {

    private  Long categoryId;
    private String categoryName;
    private List<CategoryTreeTo> categoryChild;
}
