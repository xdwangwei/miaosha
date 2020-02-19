package com.wangwi.miaosha.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPO {

    private Integer id;

    // 订单号
    private String orderNo;

    // 下单人id
    private Integer userId;

    // 商品id
    private Integer itemId;

    // 下单时的商品价格（打折，促销时不同于以往）
    private Double itemPrice;

    // 下单数目
    private Integer amount;

    // 订单总金额（下单时的单价 * 下单数目）
    private Double orderPrice;
    
    // 下单时，此商品进行的促销活动id
    private Integer promoId;
}