package com.atguigu.gmall.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
    * 三级分类表
    */
@TableName(value = "base_category3")
public class BaseCategory3 {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 三级分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 二级分类编号
     */
    @TableField(value = "category2_id")
    private Long category2Id;

    /**
     * 获取编号
     *
     * @return id - 编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取三级分类名称
     *
     * @return name - 三级分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置三级分类名称
     *
     * @param name 三级分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取二级分类编号
     *
     * @return category2_id - 二级分类编号
     */
    public Long getCategory2Id() {
        return category2Id;
    }

    /**
     * 设置二级分类编号
     *
     * @param category2Id 二级分类编号
     */
    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }
}