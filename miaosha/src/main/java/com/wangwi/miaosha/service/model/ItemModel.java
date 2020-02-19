package com.wangwi.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 15:58
 * @Description: 商品的核心领域模型
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {
    
    private Integer id;
    
    @NotBlank(message = "商品名称不能为空")
    private String title;
    
    @NotBlank(message = "商品描述不能为空")
    private String description;
    
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    // double 序列化传回前端会存在精度问题
    private BigDecimal price;
    
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;
    
    @NotNull(message = "商品库存不能为空")
    @Min(value = 0, message = "商品库存不能小于0")
    private Integer stock;
    
    // 前端创建一个商品时，传过来的信息不涉及销量
    private Integer sales;
    
    // 聚合模型，促销活动信息
    // promoModel不为空时，表示有正在进行或还未开始的秒杀活动
    private PromoModel promoModel;
}
