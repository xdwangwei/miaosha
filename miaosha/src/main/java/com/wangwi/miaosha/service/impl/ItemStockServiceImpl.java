package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.ItemStockPO;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.mapper.ItemStockPOMapper;
import com.wangwi.miaosha.service.ItemStockService;
import com.wangwi.miaosha.validator.ValidationResult;
import com.wangwi.miaosha.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 22:06
 * @Description: ItemStockServiceImpl
 **/
@Service
public class ItemStockServiceImpl implements ItemStockService {
    
    @Autowired
    private ItemStockPOMapper itemStockPOMapper;

    @Autowired
    private ValidatorImpl validator;
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        return itemStockPOMapper.decreaseStock(itemId, amount) > 0 ? true : false;
    }

    @Override
    public ItemStockPO selectByItemId(Integer itemId) throws BusinessException {
        ItemStockPO itemStockPO = itemStockPOMapper.selectByItemId(itemId);
        if (itemStockPO == null) {
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "所选商品信息不存在");
        }
        return itemStockPO;
    }

    @Override
    public void save(ItemStockPO record) throws BusinessException {
        // 入参校验
        if (record == null)
            throw new BusinessException(EmBusinessError.PARAM_INVALID);
        ValidationResult result = validator.validate(record);
        if (result.isHasErrors())
            throw new BusinessException(EmBusinessError.PARAM_INVALID, result.getValidationErrors());
        // 记录入库
        itemStockPOMapper.insertSelective(record);
    }
}
