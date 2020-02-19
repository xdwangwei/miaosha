package com.wangwi.miaosha.service;

import com.wangwi.miaosha.entity.po.ItemSalesPO;
import com.wangwi.miaosha.exception.BusinessException;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 22:09
 * @Description: ItemSalesService
 **/
public interface ItemSalesService {

    /**
     * 指定商品销量增加
     * @param itemId
     * @param amount
     */
    void increaseSales(Integer itemId, Integer amount);

    /**
     * 根据商品id获取商品销量消息
     * @param itemId
     * @return
     */
    ItemSalesPO selectByItemId(Integer itemId) throws BusinessException;

    /**
     * 插入商品销量记录
     * @param record
     */
    void save(ItemSalesPO record) throws BusinessException;
}
