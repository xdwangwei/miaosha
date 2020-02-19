package com.wangwi.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 21:49
 * @Description: 用户下单信息领域模型
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {
    
    private Integer id;
    
    // 订单号
    private String orderNo;
    
    // 下单人id
    private Integer userId;
    
    // 商品id
    private Integer itemId;
    
    // 下单时的商品价格（打折，促销时不同于以往）
    private BigDecimal itemPrice;
    
    // 下单数目
    private Integer amount;
    
    // 订单总金额（下单时的单价 * 下单数目）
    private BigDecimal orderPrice;

    // 下单时，此商品进行的促销活动id
    private Integer promoId;
}
