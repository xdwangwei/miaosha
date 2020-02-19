package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.UserPasswordPO;

public interface UserPasswordPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordPO record);

    int insertSelective(UserPasswordPO record);

    UserPasswordPO selectByPrimaryKey(Integer id);

    /**
     * 根据用户id查出其密码信息
     * @param userId
     * @return
     */
    UserPasswordPO selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPasswordPO record);

    int updateByPrimaryKey(UserPasswordPO record);
}