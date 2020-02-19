package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.ItemStockPO;
import org.apache.ibatis.annotations.Param;

public interface ItemStockPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockPO record);

    int insertSelective(ItemStockPO record);

    ItemStockPO selectByPrimaryKey(Integer id);

    ItemStockPO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemStockPO record);

    int updateByPrimaryKey(ItemStockPO record);
    
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}