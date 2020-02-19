package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.UserInfoPO;
import com.wangwi.miaosha.entity.po.UserPasswordPO;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.mapper.UserInfoPOMapper;
import com.wangwi.miaosha.mapper.UserPasswordPOMapper;
import com.wangwi.miaosha.service.UserService;
import com.wangwi.miaosha.service.model.UserModel;
import com.wangwi.miaosha.validator.ValidationResult;
import com.wangwi.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 20:02
 * @Description: UserServiceImpl
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoPOMapper userInfoPOMapper;

    @Autowired
    private UserPasswordPOMapper userPasswordPOMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer userId) {
        UserInfoPO userInfoPO = userInfoPOMapper.selectByPrimaryKey(userId);
        if (userInfoPO == null) 
            return null;
        UserPasswordPO userPasswordPO = userPasswordPOMapper.selectByUserId(userId);
        // 根据持久层对象用户基本信息和用户密码，得到用户领域模型对象
        return convertUserModelFromInfoPOAndPasswordPO(userInfoPO, userPasswordPO);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void save(UserModel userModel) throws BusinessException {
        if (userModel == null)
            throw new BusinessException(EmBusinessError.PARAM_INVALID);
        // 校验
        ValidationResult validateResult = validator.validate(userModel);
        if (validateResult.isHasErrors() == true)
            // 参数不合法，校验得到的异常信息
            throw new BusinessException(EmBusinessError.PARAM_INVALID, validateResult.getValidationErrors());
        // 由核心领域模型对象得到用户持久层对象 userModel --> userInfoPO
        UserInfoPO userInfoPO = convertUserInfoPOFromModel(userModel);
        try {
        // 保存userInfoPO,会根据主键生成策略将最终的主键值保存进userInfoPO
            userInfoPOMapper.insertSelective(userInfoPO);
            // 同一手机号多次注册
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.USER_ACCOUT_ALREADY_USE);
        }
        // 把数据库插入后得到的主键值，设置进前端传来的UserModel中
        userModel.setId(userInfoPO.getId());
        // 由核心领域模型对象得到用户持久层对象  userModel --> userPsswordPO
        UserPasswordPO userPasswordPO = convertUserPasswordPOFromModel(userModel);
        // 保存userPasswordPO
        userPasswordPOMapper.insertSelective(userPasswordPO);
    }

    @Override
    public UserModel login(String telephone, String originalPassword) throws BusinessException {
        // 根据手机号查出用户信息
        UserInfoPO userInfoPO = userInfoPOMapper.selectByTelephone(telephone);
        if (userInfoPO == null)
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAILED);
        // 根据用户id查出密码
        UserPasswordPO userPasswordPO = userPasswordPOMapper.selectByUserId(userInfoPO.getId());
        // 密码匹配失败
        if (!bCryptPasswordEncoder.matches(originalPassword, userPasswordPO.getEncrptPassword()))
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAILED);
        // 登录成功
        return convertUserModelFromInfoPOAndPasswordPO(userInfoPO, userPasswordPO);
    }


    // 根据持久层对象用户基本信息和用户密码，得到用户领域模型对象
    private UserModel convertUserModelFromInfoPOAndPasswordPO(UserInfoPO userInfoPO, UserPasswordPO userPasswordPO) {
        if (userInfoPO == null) 
            return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfoPO, userModel);
        if (userPasswordPO != null)
            userModel.setEncrptPassword(userPasswordPO.getEncrptPassword());
        return userModel;
    }
    
    // 由核心领域模型对象得到用户持久层对象
    private UserInfoPO convertUserInfoPOFromModel(UserModel userModel){
        if (userModel == null)
            return null;
        UserInfoPO userInfoPO = new UserInfoPO();
        BeanUtils.copyProperties(userModel, userInfoPO);
        return userInfoPO;
    }

    // 由核心领域模型对象得到用户持久层对象
    private UserPasswordPO convertUserPasswordPOFromModel(UserModel userModel){
        if (userModel == null)
            return null;
        UserPasswordPO userPasswordPO = new UserPasswordPO();
        // BeanUtils.copyProperties(userModel, userPasswordPO);
        userPasswordPO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordPO.setUserId(userModel.getId());
        return userPasswordPO;
    }
}
