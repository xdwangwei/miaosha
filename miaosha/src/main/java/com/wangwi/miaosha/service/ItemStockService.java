package com.wangwi.miaosha.service;

import com.wangwi.miaosha.entity.po.ItemStockPO;
import com.wangwi.miaosha.exception.BusinessException;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 22:04
 * @Description: ItemStockService
 **/
public interface ItemStockService {

    /**
     * 指定商品减库存
     * @param itemId
     * @param amount
     * @return
     */
    boolean decreaseStock(Integer itemId, Integer amount);

    /**
     * 根据商品id获取商品库存信息
     * @param itemId
     * @return
     */
    ItemStockPO selectByItemId(Integer itemId) throws BusinessException;

    /**
     * 插入商品库存信息记录
     * @param record
     * @return
     */
    void save(ItemStockPO record) throws BusinessException;
}
