package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.PromoPO;

public interface PromoPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromoPO record);

    int insertSelective(PromoPO record);

    PromoPO selectByPrimaryKey(Integer id);

    PromoPO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(PromoPO record);

    int updateByPrimaryKey(PromoPO record);
}