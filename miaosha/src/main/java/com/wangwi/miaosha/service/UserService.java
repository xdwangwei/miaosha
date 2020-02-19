package com.wangwi.miaosha.service;

import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.service.model.UserModel;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 20:00
 * @Description: UserService
 **/
public interface UserService {

    /**
     * 根据用户id获取用户所有属性信息
     * @param userId
     * @return
     */
    UserModel getUserById(Integer userId);

    /**
     * 由核心领域模型对象保存用户信息
     * @param userModel
     */
    void save(UserModel userModel) throws BusinessException;

    UserModel login(String telephone, String originalPassword) throws BusinessException;
}
