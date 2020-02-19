package com.wangwi.miaosha.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceInfoPO {
    // 主键
    private String sequenceName;

    // 当前值
    private Integer currentValue;

    // 增加多少
    private Integer increaseStep;

    // 最大值
    private Integer maxValue;

    // 初始值
    private Integer initValue;
    
}