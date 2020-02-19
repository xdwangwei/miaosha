package com.wangwi.miaosha.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPO {
    
    private Integer id;

    private String title;

    private String description;

    private Double price;

    private String imgUrl;

}