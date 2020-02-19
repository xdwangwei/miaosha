package com.wangwi.miaosha.entity.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoPO {
    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartAccount;
    
}