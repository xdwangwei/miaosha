package com.wangwi.miaosha.controller.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 19:00
 * @Description: 用户视图模型对象：用于返回给前端
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telephone;
}
