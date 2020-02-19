package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.ItemPO;

import java.util.List;

public interface ItemPOMapper {
    
    List<ItemPO> itemList();
    
    int deleteByPrimaryKey(Integer id);

    int insert(ItemPO record);

    int insertSelective(ItemPO record);

    ItemPO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemPO record);

    int updateByPrimaryKey(ItemPO record);
}