package com.wangwi.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.rmi.MarshalException;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 19:00
 * @Description: 用户领域模型：包含用户所有信息
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "性别不能不选")
    private Integer gender;

    @NotNull(message = "年龄不能不填")
    @Min(value = 0, message = "年龄不能小于0")
    @Max(value = 120, message = "年龄必须小于120")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @NotBlank(message = "密码不能为空")
    private String encrptPassword;
    
    private String registerMode;

    private String thirdPartAccount;

}
