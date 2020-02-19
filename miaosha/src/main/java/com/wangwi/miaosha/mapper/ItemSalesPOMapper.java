package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.ItemSalesPO;
import org.apache.ibatis.annotations.Param;

public interface ItemSalesPOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemSalesPO record);

    int insertSelective(ItemSalesPO record);

    ItemSalesPO selectByPrimaryKey(Integer id);
    
    ItemSalesPO selectByItemId(Integer itemId);

    int updateByPrimaryKeySelective(ItemSalesPO record);

    int updateByPrimaryKey(ItemSalesPO record);

    void increaseSales(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}