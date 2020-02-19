package com.wangwi.miaosha.service;

import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 16:16
 * @Description: ItemService
 **/
public interface ItemService {

    /**
     * 根据传过来的ItemModel对象，将新商品信息插入数据库，并返回从数据库中重新取回的信息
     * @param itemModel
     * @return
     */
    ItemModel saveItem(ItemModel itemModel) throws BusinessException;

    /**
     * 通过商品编号id，获取此商品完整信息
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id) throws BusinessException;

    /**
     * 获取全部商品信息列表
     * @return
     */
    List<ItemModel> getItemList() throws BusinessException;
    
    
}
