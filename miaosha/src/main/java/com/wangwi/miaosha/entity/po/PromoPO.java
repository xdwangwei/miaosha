package com.wangwi.miaosha.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoPO {
    private Integer id;

    private String promoName;

    private Integer itemId;

    private Double promoPrice;

    private Date startTime;

    private Date endTime;
    
}