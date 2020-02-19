package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.UserInfoPO;

public interface UserInfoPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoPO record);

    int insertSelective(UserInfoPO record);

    UserInfoPO selectByPrimaryKey(Integer id);
    
    UserInfoPO selectByTelephone(String telephone);

    int updateByPrimaryKeySelective(UserInfoPO record);

    int updateByPrimaryKey(UserInfoPO record);
}