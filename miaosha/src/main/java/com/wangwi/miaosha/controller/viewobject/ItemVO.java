package com.wangwi.miaosha.controller.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 16:03
 * @Description: 用于前端展示的商品信息模型对象
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {

    // 商品编号id
    private Integer id;

    // 商品标题名称
    private String title;

    // 商品描述
    private String description;

    // 商品价格
    // double 序列化传回前端会存在精度问题
    private BigDecimal price;

    // 商品描述图片路径
    private String imgUrl;

    // 商品库存
    private Integer stock;

    // 前端创建一个商品时，传过来的信息不涉及销量
    private Integer sales;
    
    // 若此商品存在促销活动，则返回给前端时会包含这些信息
    // 促销活动id
    private Integer promoId;
    // 促销状态
    private Integer promoStatus;
    // 促销价格
    private BigDecimal promoPrice;
    
    // joda DateTime json序列化较为复杂，字符串形式返回给前端容易解析
    // 促销活动开始时间
    // private DateTime promoStartTime;
    private String promoStartTime;
    // 促销活动结束时间
    // private DateTime promoEndTime;
    private String promoEndTime;
}
