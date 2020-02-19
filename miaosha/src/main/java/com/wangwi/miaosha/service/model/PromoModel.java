package com.wangwi.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: wangwei
 * @Time: 2020/2/15 周六 13:06
 * @Description: 秒杀模型
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoModel {
    
    private Integer id;
    // 秒杀活动名称
    private String promoName;
    // 秒杀活动商品id
    private Integer itemId;
    // 秒杀价格
    private BigDecimal promoPrice;
    // 秒杀活动开始时间
    private DateTime startTime;
    // 秒杀活动结束时间
    private DateTime endTime;
    
    // 秒杀活动状态 1 未开始；2 正在进行；3 已结束
    private Integer status;
}
