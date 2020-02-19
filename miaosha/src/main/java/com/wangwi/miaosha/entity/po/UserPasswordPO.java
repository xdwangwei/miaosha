package com.wangwi.miaosha.entity.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordPO {
    
    private Integer id;

    private Integer userId;

    private String encrptPassword;
    
}